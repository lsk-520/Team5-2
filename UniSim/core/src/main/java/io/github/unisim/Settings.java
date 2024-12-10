package io.github.unisim;

/**
 * Contains global settings for the game such as volume and username.
 */
public class Settings {
  private float volume = 1.0f;
  private String username = "";

  public float getVolume() {
    return volume;
  }

  public void setVolume(float volume) {
    this.volume = volume;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
