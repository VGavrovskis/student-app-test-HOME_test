package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static utils.ConfigHelper.getConfig;

public class DriverManager {

    private static ThreadLocal<WebDriver>driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getInstance() {
        if(driverThreadLocal.get() == null) {
            if(getConfig().getBoolean("student.app.run.locally")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driverThreadLocal.set(new ChromeDriver());
                }  else  {
                ChromeOptions browserOptions = new ChromeOptions();
                browserOptions.setPlatformName("Windows 8");
                browserOptions.setBrowserVersion("108");
                Map<String, Object> sauceOptions = new HashMap<>();
                sauceOptions.put("build", "selenium-build-V0CVY");
                sauceOptions.put("name", "<your test name>");
                browserOptions.setCapability("sauce:options", sauceOptions);

                URL url = null;
                try {
                    url = new URL("https://oauth-vjaceslavs.gavrovskis-78636:d6ed8caa-18ef-4b3e-8056-d86ed96ae609@ondemand.us-west-1.saucelabs.com:443/wd/hub");
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                driverThreadLocal.set(new RemoteWebDriver(url, browserOptions));
            }
        }
        return driverThreadLocal.get();
    }
}

