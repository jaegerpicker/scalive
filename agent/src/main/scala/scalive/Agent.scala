package scalive

import java.lang.instrument.Instrumentation
import java.net.ServerSocket

object Agent {
  private var portOpen = false

  /**
   * @param agentArgs <jarpath> <server port> [class loader id]
   */
  def agentmain(agentArgs: String, inst: Instrumentation) {
    val args    = agentArgs.split(" ")
    val jarpath = args(0)

    // Add scala-library.jar immediately so that we can write Scala code
    // (the above must be Java code)

    val port = args(1).toInt
    val clId = if (args.length == 3) Some(args(2).toInt) else None

    // Need to start a new thread because:
    // - startTcpRepl blocks until a connection comes in
    // - VirtualMachine#loadAgent at the client does not return until this
    //   agentmain method returns
    // - The client only connects to the server after VirtualMachine#loadAgent
    //   returns
    new Thread(new Runnable {
      override def run() { startTcpRepl(port) }
    }).start()

    while (!portOpen) Thread.sleep(100)
  }

  private def startTcpRepl(port: Int) {
    println("[Scalive] REPL server starts at port " + port)
    val server = new ServerSocket(port)
    portOpen   = true

    val client = server.accept()  // Block until a connection comes in
    val repl   = new Repl(client.getInputStream, client.getOutputStream)
    repl.start()
  }
}
