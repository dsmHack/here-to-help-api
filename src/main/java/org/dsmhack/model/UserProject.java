/*
 * Here to Help
 * This is a restful web service used to log hours for non-profits to submit for money grants.
 *
 * OpenAPI spec version: 1.0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.dsmhack.model;

import com.google.gson.Gson;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class UserProject {
  @EmbeddedId
  private MyKey myKey;

  public MyKey getMyKey() {
    return myKey;
  }

  public void setMyKey(MyKey myKey) {
    this.myKey = myKey;
  }

  public String toJson() {
    return new Gson().toJson(this);
  }

  @Embeddable
  public static class MyKey implements Serializable {
    @Column
    private String userGuid;

    @Column
    private String projGuid;

    public String getUserGuid() {
      return userGuid;
    }

    public void setUserGuid(String userGuid) {
      this.userGuid = userGuid;
    }

    public String getProjGuid() {
      return projGuid;
    }

    public void setProjGuid(String projGuid) {
      this.projGuid = projGuid;
    }
  }
}

