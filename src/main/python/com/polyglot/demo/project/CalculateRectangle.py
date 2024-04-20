import java

Rectangle = java.type("com.polyglot.demo.project.classes.Rectangle")

def calcArea(rectangle: Rectangle) -> float:
    return rectangle.getArea()

rectangle = Rectangle(4.0, 2.5)
calcArea(rectangle)
