import unittest

# Product = java.type("com.polyglot.demo.project.entity.Product") # alreay throws exception: host symbol com.polyglot.demo.project.entity.Product is not defined or access has been denied

import RecommendationServiceImpl # throws Exception, same problem with importing Product here

class Product():
    def getEan():
        return "123456"


class RecommendationServiceImplTest(unittest.TestCase):

    def testRecommend(self):

        product = Product()

        with self.assertRaises(Exception) as context:
        
            RecommendationServiceImpl.recommend(product)
             # ...


if __name__ == '__main__':
    unittest.main()