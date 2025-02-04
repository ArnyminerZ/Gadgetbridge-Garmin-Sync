import json
import os
import sys
from selenium.common import NoSuchElementException
from selenium.webdriver.chrome.webdriver import WebDriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
from selenium.webdriver.support.wait import WebDriverWait
from time import sleep

from drivers.seleniumbase_driver import get_driver

root_dir = os.path.dirname(os.path.realpath(__file__))
output_csv = os.path.join(root_dir, 'output.csv')

if not os.path.exists('screenshots'):
    os.makedirs('screenshots')

def store_cookies(driver: WebDriver):
    cookies = driver.get_cookies()
    with open('cookies.json', 'w') as file:
        json.dump(cookies, file)

def load_cookies(driver: WebDriver):
    if not os.path.exists('cookies.json'):
        return
    with open('cookies.json', 'r') as file:
        cookies_list = json.load(file)
        for cookie in cookies_list:
            driver.add_cookie(cookie)
    driver.refresh()

def main(email: str, password: str):
    driver: WebDriver = get_driver('https://connect.garmin.com/signin')

    try:
        driver.implicitly_wait(2)
        wait = WebDriverWait(driver, timeout=2)

        load_cookies(driver)

        print('Waiting for redirection...')
        wait.until(lambda d: ('modern/home' in d.current_url) or 'sso.garmin.com' in d.current_url)
        login_required = 'sso.garmin.com' in driver.current_url

        if login_required:
            print('Waiting for form...')
            wait.until(lambda d: driver.find_element(By.ID, 'email').is_displayed())

            print('Filling form...')
            driver.find_element(By.ID, 'email').send_keys(email)
            driver.find_element(By.ID, 'password').send_keys(password)
            driver.find_element(By.CLASS_NAME, 'signin__form__input--remember').click()

            print('Submitting...')
            driver.find_element(By.CSS_SELECTOR, 'button[type=submit]').click()

            print('Waiting until redirected to Garmin Connect...')
            wait.until(lambda d: 'connect.garmin.com' in d.current_url)
            sleep(1)

            try:
                print('Accepting cookies popup...')
                driver.find_element(By.ID, 'truste-consent-required').click()
            except NoSuchElementException:
                print('Cookies already accepted')

            store_cookies(driver)

        driver.save_screenshot('screenshots/modern.png')

        print("Waiting for 3 seconds...")
        sleep(3)

        print('Launching data import utility...')
        driver.get('https://connect.garmin.com/modern/import-data')
        sleep(1)

        file_input = driver.find_element(By.CLASS_NAME, 'dzu-input')
        file_input.send_keys(output_csv)
        driver.save_screenshot('screenshots/import.png')
        sleep(1)

        driver.find_element(By.XPATH, '//button[text()="Import Data"]').click()

        print('Selecting import preferences...')
        select_elements = driver.find_elements(By.TAG_NAME, 'select')
        Select(select_elements[0]).select_by_value('EN') # English
        Select(select_elements[1]).select_by_value('metric') # Centimeters, Meters, Kilometers
        Select(select_elements[2]).select_by_value('metric') # Kilograms
        Select(select_elements[3]).select_by_value('YEAR_DASH') # 2025-12-31
        Select(select_elements[4]).select_by_value('.') # 1,234.56

        print('Submitting import...')
        driver.find_element(By.XPATH, '//button[text()="Continue"]').click()

        sleep(5)
        driver.save_screenshot('screenshots/complete.png')
    except Exception as e:
        driver.save_screenshot('screenshots/error.png')

        logs = driver.get_log("browser")
        print('Logs:')
        print(logs)
        print()

        raise e
    finally:
        driver.close()
        driver.quit()

if __name__ == '__main__':
    email = sys.argv[1]
    password = sys.argv[2]

    if email is None or len(email) <= 0:
        print('Please, provide an email for logging in.')
        print('Command usage: garmin-upload.py <email> <password>')
    if password is None or len(password) <= 0:
        print('Please, provide a password for logging in.')
        print('Command usage: garmin-upload.py <email> <password>')

    main(email, password)
