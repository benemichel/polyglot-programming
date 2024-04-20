def show(action)  { 
    println ([of: { n -> action (n) }])
}

square_root = { Math.sqrt(it) }

show square_root of 9