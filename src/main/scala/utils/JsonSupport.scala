package utils

import blockchain.{Chain, ChainLink, EmptyChain, Transaction}
import spray.json._

object JsonSupport extends DefaultJsonProtocol {
  implicit object ChainLinkJsonFormat extends RootJsonFormat[ChainLink] {
    override def read(json: JsValue): ChainLink =
      json.asJsObject.getFields("index", "proof", "values", "previousHash", "timestamp", "tail") match {
        case Seq(JsNumber(index), JsNumber(proof), values, JsString(previousHash), JsNumber(timestamp), tail) =>
          ChainLink(index.toInt, proof.toLong, values.convertTo[List[Transaction]], previousHash, timestamp.toLong, tail.convertTo(ChainJsonFormat))
        case _ => throw DeserializationException("Cannot deserialize: ChainLink expected")
      }

    def write(obj: ChainLink): JsValue = JsObject(
      "index" -> JsNumber(obj.index),
      "proof" -> JsNumber(obj.proof),
      "values" -> JsArray(obj.values.map(_.toJson).toVector),
      "previousHash" -> JsString(obj.previousHash),
      "timestamp" -> JsNumber(obj.timestamp),
      "tail" -> ChainJsonFormat.write(obj.tail),
    )
  }

  implicit object ChainJsonFormat extends RootJsonFormat[Chain] {
    def read(json: JsValue): Chain = {
      json.asJsObject.getFields("previousHash") match {
        case Seq(_) => json.convertTo[ChainLink]
        case Seq() => EmptyChain
      }
    }

    def write(obj: Chain): JsValue = obj match {
      case link: ChainLink => link.toJson
      case EmptyChain => JsObject(
        "index" -> JsNumber(EmptyChain.index),
        "hash" -> JsString(EmptyChain.hash),
        "values" -> JsArray(),
        "proof" -> JsNumber(EmptyChain.proof),
        "timestamp" -> JsNumber(EmptyChain.timestamp),
      )
    }
  }

  implicit object TransactionJsonFormat extends RootJsonFormat[Transaction] {
    def read(json: JsValue): Transaction = {
      json.asJsObject.getFields("sender", "recipient", "value") match {
        case Seq(JsString(sender), JsString(recipient), JsNumber(amount)) =>
          Transaction(sender, recipient, amount.toLong)
        case _ => throw DeserializationException("Cannot deserialize: Transaction expected")
      }
    }

    def write(obj: Transaction): JsValue = JsObject(
      "sender" -> JsString(obj.sender),
      "recipient" -> JsString(obj.recipient),
      "value" -> JsNumber(obj.value),
    )
  }
}
