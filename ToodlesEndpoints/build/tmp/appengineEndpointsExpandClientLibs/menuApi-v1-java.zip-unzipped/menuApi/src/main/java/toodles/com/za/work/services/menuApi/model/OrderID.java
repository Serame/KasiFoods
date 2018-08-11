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
 * on 2018-06-10 at 19:43:20 UTC 
 * Modify at your own risk.
 */

package toodles.com.za.work.services.menuApi.model;

/**
 * Model definition for OrderID.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the menuApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class OrderID extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<ToodlesMessage> messages;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("order_id")
  private java.lang.Integer orderId;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<ToodlesMessage> getMessages() {
    return messages;
  }

  /**
   * @param messages messages or {@code null} for none
   */
  public OrderID setMessages(java.util.List<ToodlesMessage> messages) {
    this.messages = messages;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getOrderId() {
    return orderId;
  }

  /**
   * @param orderId orderId or {@code null} for none
   */
  public OrderID setOrderId(java.lang.Integer orderId) {
    this.orderId = orderId;
    return this;
  }

  @Override
  public OrderID set(String fieldName, Object value) {
    return (OrderID) super.set(fieldName, value);
  }

  @Override
  public OrderID clone() {
    return (OrderID) super.clone();
  }

}