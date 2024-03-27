class WithPrivateMethod

  def public_method()
    return 42
  end

  def private_method()
    return 42
  end

  private :private_method

end

withPrivateMethod = WithPrivateMethod.new()