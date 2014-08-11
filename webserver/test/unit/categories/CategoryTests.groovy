package categories



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(categories.Category)
class CategoryTests {

    def registeredParentCategory
    def registeredChildCategory
    def sampleCategory

    @Before
    void setUp(){

        String categoryId
        String parentCategoryId
        String name
        String status = "active"
        Date dateRegistration = new Date()
        Date dateUpdate = new Date()
        String siteId = 'MB'

        registeredParentCategory = new Category(

                name:'Servicios'

        )

        mockForConstraintsTests(Category, [registeredParentCategory])

        registeredChildCategory = new Category(
                parentCategoryId: registeredParentCategory.id,
                name: 'Mariachi'

        )

        mockForConstraintsTests(Category,[registeredChildCategory])

        sampleCategory = new Category(

                parentCategoryId:registeredParentCategory.id,
                name:'Sinaloense'

        )

    }

    void test_CategoryIsValidate(){
        assertTrue(sampleCategory.validate())
        sampleCategory.save()
        assertEquals(sampleCategory.parentCategoryId, registeredChildCategory.parentCategoryId)
    }

    void test_CategoryIsNotValidWhit_NameIsLong(){
        sampleCategory.name = 'si el nombre excede los 150 caracteres entonces no podria conciderarse que es un nombre de categoria valido ya que este nombre es demasdiado largo para que se auna categoria'
        assertFalse(sampleCategory.validate())
        assertEquals('the name should be less than 150 characters', 'maxSize', sampleCategory.errors['name'])
    }

    void test_CategoryIsNotValidWhit_ParentCategoryIdIsVeryBig(){
        sampleCategory.parentCategoryId = "897120912847019284701298471029847120948712908172"
        assertFalse(sampleCategory.validate())
        assertEquals('The parentCategoryId should be less then 20 characteres','maxSize', sampleCategory.errors['parentCategoryId'])
    }

    void test_CategoryIsNotValidWhit_StatusNotInList(){
        sampleCategory.status = 'revised'
        assertFalse(sampleCategory.validate())
        assertEquals('The status is not valid','inList',sampleCategory.errors['status'])
    }

}
