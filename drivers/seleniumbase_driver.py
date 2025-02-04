from seleniumbase import Driver

def get_driver(url: str = "https://www.scrapingcourse.com/cloudflare-challenge") -> Driver:
    # initialize the driver in GUI mode with UC enabled
    driver = Driver(uc=True, headless=False)

    # open URL with a 6-second reconnect time to bypass the initial JS challenge
    driver.uc_open_with_reconnect(url, reconnect_time=6)

    # attempt to click the CAPTCHA checkbox if present
    driver.uc_gui_click_captcha()

    return driver
