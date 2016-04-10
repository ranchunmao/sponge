package progscala2.introscala.shapes

case class Point(x: Double = 0.0, y: Double = 0.0)

abstract class Shape() {
    // draw makes a function argument.
    // each shape will pass a stringized version of itself to this function, which does the "drawing"
    // the idea is that callers of draw will pass a function that does the actual drawing when given a string representation of the shape
    // demonstrates the idea that functions are first-class values, just like "strings", "ints", "points", and other objects.
    // like other values, we can assign functions to variables, pass them to other functions as arguments, as in "draw", and return them from functions.
    // we will use this feature as a powerful tool for building composable, yet flexible software
    def draw(f: String => Unit): Unit = f(s"draw: ${this.toString}")
}

case class Circle(center: Point, radius: Double) extends Shape

case class Rectangle(lowerLeft: Point, height: Double, width: Double) extends Shape

case class Triangle(point1: Point, point2: Point, point3: Point) extends Shape
