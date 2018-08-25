/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2018-05-04 17:28:03 UTC)
 * on 2018-08-25 at 11:50:18 UTC 
 * Modify at your own risk.
 */

package toodles.com.za.work.services.menuApi.model;

/**
 * Model definition for User.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the menuApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class User extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String cell;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long custID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dateofbirth;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String gender;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String loginType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String password;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String role;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String surname;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCell() {
    return cell;
  }

  /**
   * @param cell cell or {@code null} for none
   */
  public User setCell(java.lang.String cell) {
    this.cell = cell;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCustID() {
    return custID;
  }

  /**
   * @param custID custID or {@code null} for none
   */
  public User setCustID(java.lang.Long custID) {
    this.custID = custID;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDateofbirth() {
    return dateofbirth;
  }

  /**
   * @param dateofbirth dateofbirth or {@code null} for none
   */
  public User setDateofbirth(java.lang.String dateofbirth) {
    this.dateofbirth = dateofbirth;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public User setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGender() {
    return gender;
  }

  /**
   * @param gender gender or {@code null} for none
   */
  public User setGender(java.lang.String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLoginType() {
    return loginType;
  }

  /**
   * @param loginType loginType or {@code null} for none
   */
  public User setLoginType(java.lang.String loginType) {
    this.loginType = loginType;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public User setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPassword() {
    return password;
  }

  /**
   * @param password password or {@code null} for none
   */
  public User setPassword(java.lang.String password) {
    this.password = password;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRole() {
    return role;
  }

  /**
   * @param role role or {@code null} for none
   */
  public User setRole(java.lang.String role) {
    this.role = role;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSurname() {
    return surname;
  }

  /**
   * @param surname surname or {@code null} for none
   */
  public User setSurname(java.lang.String surname) {
    this.surname = surname;
    return this;
  }

  @Override
  public User set(String fieldName, Object value) {
    return (User) super.set(fieldName, value);
  }

  @Override
  public User clone() {
    return (User) super.clone();
  }

}
