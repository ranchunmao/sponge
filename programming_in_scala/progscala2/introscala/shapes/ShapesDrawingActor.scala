package progscala2.introscala.shapes

// declare object "Messages" that defines most of the messages we'll send between actors
// they work like "signals" to trigger behavior.
// putting them in an object is a common encapsulation idiom.
object Messages {
    // "Exit" and "Finished" have no state of their own, so they function like "flags"
    object Exit
    object Finished
    // the case class "Response" is used to send an arbitrary string message to a sender in response to a message received from the sender
    case class Response(message: String)
}

// import "Actor" type, an abstract based class, which we'll subclass to define our actors
import akka.actor.Actor

// define an actor for drawing shapes
// in most actor systems, including Akka, there is a "mailbox" associated with each actor where messages are stored until they can be processed by the actor.
// Akka guarantees that messages are processed in the order in which they are received, 
// and it guarantees that while each message is being processed, the code won't preempted by another thread, so the handler code is inherently thread-safe.
class ShapesDrawingActor extends Actor {
    // import three messages defined in "Messages" here
    // nesting imports, which is permitted in scala, scopes these values to where they are used
    import Messages._

    // implement the one abstract method "Actor.receive" that we have to implement, which defines how to handle incoming messages
    // takes no arguments
    // the body is just a sequence of expressions starting with the "case" keyword
    // "PartialFunction"
    // a "PartialFunction" consists only of "case" clauses, which do "pattern matching" on the message that will be passed to the function
    // there is no function argument shown for the message. it's handled internally by the implementation.
    def receive = {
        case s:Shape =>
            s.draw(str => println(s"ShapesDrawingActor: $str"))
            // the choice of ! is a convention adopted from Erlang, a language that popularized the actor model
            // cleaner appearance
            // "infix notation", because the "operator" is between object and argument
            sender ! Response(s"ShapesDrawingActor: $s drawn")
        case Exit => 
            println(s"ShapesDrawingActor: exiting...")
            sender ! Finished
        case unexpected =>
            val response = Response(s"ERROR: Unknown message: $unexpected")
            println(s"ShapesDrawingActor: $response")
            sender ! response
    }
}
