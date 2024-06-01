package pow

import crypto.Crypto
import spray.json.DefaultJsonProtocol._
import spray.json._

import scala.annotation.tailrec

object ProofOfWork {
  def work(lastHash: String): Long = {
    @tailrec
    def _work(lastHash: String, proof: Long): Long = {
      if (validProof(lastHash, proof))
        proof
      else
        _work(lastHash, proof + 1)
    }

    val proof = 0
    _work(lastHash, proof)
  }

  def validProof(lastHash: String, proof: Long): Boolean = {
    val guess = (lastHash ++ proof.toString).toJson.toString()
    val guessHash = Crypto.sha256Hash(guess)
    (guessHash take 4) == "0000"
  }
}
