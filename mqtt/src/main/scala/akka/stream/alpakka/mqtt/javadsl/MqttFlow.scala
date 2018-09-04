/*
 * Copyright (C) 2016-2018 Lightbend Inc. <http://www.lightbend.com>
 */

package akka.stream.alpakka.mqtt.javadsl

import java.util.concurrent.CompletionStage

import akka.Done
import akka.stream.alpakka.mqtt._
import akka.stream.javadsl.Flow

import scala.compat.java8.FutureConverters._

/**
 * Java API
 *
 * MQTT flow factory.
 */
object MqttFlow {

  /**
   * Create a flow to send messages to MQTT AND subscribe to MQTT messages (without a commit handle).
   *
   * The materialized value completes on successful connection to the MQTT broker.
   *
   * @param defaultQos Quality of service level applied for messages not specifying a message specific value
   * @deprecated use atMostOnce() instead
   */
  @deprecated("use atMostOnce instead", "0.21")
  @java.lang.Deprecated
  def create(sourceSettings: MqttSourceSettings,
             bufferSize: Int,
             defaultQos: MqttQoS): Flow[MqttMessage, MqttMessage, CompletionStage[Done]] =
    atMostOnce(sourceSettings.connectionSettings,
               MqttSubscriptions(sourceSettings.subscriptions),
               bufferSize,
               defaultQos)

  /**
   * Create a flow to send messages to MQTT AND subscribe to MQTT messages (without a commit handle).
   *
   * The materialized value completes on successful connection to the MQTT broker.
   *
   * @param defaultQos Quality of service level applied for messages not specifying a message specific value
   * @deprecated use atMostOnce() instead
   */
  @deprecated("use atMostOnce with MqttConnectionSettings and MqttSubscriptions instead", "0.21")
  @java.lang.Deprecated
  def atMostOnce(settings: MqttSourceSettings,
                 bufferSize: Int,
                 defaultQos: MqttQoS): Flow[MqttMessage, MqttMessage, CompletionStage[Done]] =
    atMostOnce(settings.connectionSettings, MqttSubscriptions(settings.subscriptions), bufferSize, defaultQos)

  /**
   * Create a flow to send messages to MQTT AND subscribe to MQTT messages (without a commit handle).
   *
   * The materialized value completes on successful connection to the MQTT broker.
   *
   * @param defaultQos Quality of service level applied for messages not specifying a message specific value
   */
  def atMostOnce(settings: MqttConnectionSettings,
                 subscriptions: MqttSubscriptions,
                 bufferSize: Int,
                 defaultQos: MqttQoS): Flow[MqttMessage, MqttMessage, CompletionStage[Done]] =
    scaladsl.MqttFlow
      .atMostOnce(settings, subscriptions, bufferSize, defaultQos)
      .mapMaterializedValue(_.toJava)
      .asJava

  /**
   * Create a flow to send messages to MQTT AND subscribe to MQTT messages with a commit handle to acknowledge message reception.
   *
   * The materialized value completes on successful connection to the MQTT broker.
   *
   * @param defaultQos Quality of service level applied for messages not specifying a message specific value
   * @deprecated use atLeastOnce with MqttConnectionSettings and MqttSubscriptions instead
   */
  @deprecated("use atLeastOnce with MqttConnectionSettings and MqttSubscriptions instead", "0.21")
  @java.lang.Deprecated
  def atLeastOnce(
      settings: MqttSourceSettings,
      bufferSize: Int,
      defaultQos: MqttQoS
  ): Flow[MqttMessage, MqttCommittableMessage, CompletionStage[Done]] =
    atLeastOnce(settings.connectionSettings, MqttSubscriptions(settings.subscriptions), bufferSize, defaultQos)

  /**
   * Create a flow to send messages to MQTT AND subscribe to MQTT messages with a commit handle to acknowledge message reception.
   *
   * The materialized value completes on successful connection to the MQTT broker.
   *
   * @param defaultQos Quality of service level applied for messages not specifying a message specific value
   */
  def atLeastOnce(
      settings: MqttConnectionSettings,
      subscriptions: MqttSubscriptions,
      bufferSize: Int,
      defaultQos: MqttQoS
  ): Flow[MqttMessage, MqttCommittableMessage, CompletionStage[Done]] =
    scaladsl.MqttFlow
      .atLeastOnce(settings, subscriptions, bufferSize, defaultQos)
      .map(MqttCommittableMessage.toJava)
      .mapMaterializedValue(_.toJava)
      .asJava
}
