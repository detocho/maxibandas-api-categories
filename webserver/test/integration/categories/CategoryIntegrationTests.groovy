package categories

import static org.junit.Assert.*
import org.junit.*

class CategoryIntegrationTests {

    CategoryController categoryController
    Category sampleCategory

    @Before
    void setUp() {

        sampleCategory = new Category(
                name:"Generos musicales"
        ).save()

        categoryController = new CategoryController()
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void test_must_get_Category_whit_categoryId(){

        categoryController.params.categoryId = 1
        def response =  categoryController.getCategory()
        print response

    }
}
