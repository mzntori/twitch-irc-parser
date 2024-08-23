import msg.PingMessage
import okhttp3.*

import java.util.concurrent.TimeUnit
import kotlin.reflect.typeOf

fun main() {
    val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    val websocket: WebSocket = client.newWebSocket(
        Request.Builder().url("ws://irc-ws.chat.twitch.tv").build(),
        WebSocketTwitch()
    )

    client.dispatcher.executorService.shutdown()
}

class WebSocketTwitch() : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        println(response.message)
        webSocket.send("PASS hello")
        webSocket.send("NICK justinfan1222")
        webSocket.send("CAP REQ :twitch.tv/membership twitch.tv/tags twitch.tv/commands")
        webSocket.send("JOIN #gaygebot,#mzntori")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val parser = TwitchIRCParser()
        val messagesRaw = text.split("\r\n")

        for (messageRaw in messagesRaw) {
            val parsedMessage = parser.parse(messageRaw) ?: continue
            println(parsedMessage.raw)
            val promotedMessage = parsedMessage.promoteThrowing()

            if (parsedMessage is PingMessage) {
                val pong = parsedMessage.generatePong()
                webSocket.send(pong)
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(1000, null)
        println("$code: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        t.printStackTrace()
    }
}