package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:application.properties")
public interface AppConfig extends Config {

    @Key("url")
    String url();

    @Key("default.browser")
    String browserName();
}
