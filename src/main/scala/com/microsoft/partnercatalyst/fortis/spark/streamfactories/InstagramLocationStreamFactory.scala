package com.microsoft.partnercatalyst.fortis.spark.streamfactories

import com.github.catalystcode.fortis.spark.streaming.Instagram
import com.github.catalystcode.fortis.spark.streaming.instagram.{InstagramAuth, InstagramUtils}
import com.microsoft.partnercatalyst.fortis.spark.streamprovider.{ConnectorConfig, StreamFactory}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

class InstagramLocationStreamFactory extends StreamFactory[Instagram]{
  /**
    * Creates a DStream for a given connector config iff the connector config is supported by the stream factory.
    * The param set allows the streaming context to be curried into the partial function which creates the stream.
    *
    * @param streamingContext The Spark Streaming Context
    * @return A partial function for transforming a connector config
    */
  override def createStream(streamingContext: StreamingContext): PartialFunction[ConnectorConfig, DStream[Instagram]] = {
    case ConnectorConfig("InstagramLocation", params) => {
      val auth = InstagramAuth(params("authToken"))

      InstagramUtils.createLocationStream(
        streamingContext,
        auth,
        latitude = params("latitude").toDouble,
        longitude = params("longitude").toDouble)
    }
  }
}
