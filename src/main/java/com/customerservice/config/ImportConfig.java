package com.customerservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "import-config")
@Configuration
public class ImportConfig
{
  private String connectionUrl;
  private String username;
  private String password;

  public String getConnectionUrl()
  {
    return connectionUrl;
  }

  public void setConnectionUrl(String connectionUrl)
  {
    this.connectionUrl = connectionUrl;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

}
