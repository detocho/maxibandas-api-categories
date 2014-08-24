package categories

import java.text.MessageFormat
import org.apache.ivy.plugins.conflict.ConflictManager
import grails.converters.*
import categories.exceptions.NotFoundException
import categories.exceptions.ConflictException
import categories.exceptions.BadRequestException

class CategoryService {

    static transactional = "mongo"

    def getCategory( def categoryId){

        Map jsonResult   = [:]
        def jsonChildren = []
        Map resultParentCategory = [:]

        if (!categoryId){
            throw  new NotFoundException("You must provider categoryId")
        }

        def category = Category.findById(categoryId)

        if (!category){
            throw  new NotFoundException("The categoryId not found")
        }

        def childrenCategories = Category.findAllByParentCategoryId(categoryId)
        childrenCategories.each{
            jsonChildren.add(
                    categoryId : it.id,
                    name : it.name
            )
        }



        if(category.parentCategoryId){

            def parentCategory = Category.findById(category.parentCategoryId)
            resultParentCategory.id  = parentCategory.id
            resultParentCategory.name = parentCategory.name

        }

        jsonResult.id  = category.id
        jsonResult.name = category.name
        jsonResult.parent_category=resultParentCategory
        jsonResult.children_categories = jsonChildren


        jsonResult

    }



    def createCategory(def parentCategoryId,def jsonCategory){

        Map jsonResult = [:]
        def responseMenssage = ''

        if (!Category.findById(parentCategoryId)){

            throw  new NotFoundException("The categoryId = "+parentCategoryId+" not found")

        }

        def newCategory =  new Category(

                parentCategoryId: parentCategoryId,
                name:jsonCategory?.name
        )

        if(!newCategory.validate()) {
            newCategory.errors.allErrors.each {
                responseMenssage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }

            throw new BadRequestException(responseMessage)

        }

        newCategory.save()

        jsonResult.id                = newCategory.id
        jsonResult.name              = newCategory.name
        jsonResult.parent_category   = newCategory.parentCategoryId

        jsonResult


    }

    def createCategory(def jsonCategory){

        Map jsonResult = [:]
        def responseMenssage = ''

        def newCategory = new Category(
                name:jsonCategory?.name
        )

        if(!newCategory.validate()){
            newCategory.errors.allErrors.each{
                responseMenssage+=MessageFormat.format(it.defaultMessage,it.arguments) + " "
            }
            throw new BadRequestException(responseMenssage)
        }

        newCategory.save()

        jsonResult.id                = newCategory.id
        jsonResult.name              = newCategory.name
        jsonResult.parent_category   = newCategory.parentCategoryId

        jsonResult

    }




    def modifyCategory(def categoryId, def jsonCategory){

        Map jsonResult = [:]
        def responseMessage = ''

        if (!categoryId){
            throw  new NotFoundException("You must provider categoryId")
        }

        def obteinedCategory = Category.findById(categoryId)

        if (!obteinedCategory){
            throw new  NotFoundException("The Category with categoryId="+categoryId+" not found")
        }

        obteinedCategory.name                = jsonCategory?.name
        obteinedCategory.parentCategoryId    = jsonCategory?.parent_category_id
        obteinedCategory.status              = jsonCategory?.status
        obteinedCategory.dateUpdate          = new Date()

        if(!obteinedCategory.validate()){

            obteinedCategory.errors.allErrors.each {
                responseMessage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }
            throw new BadRequestException(responseMessage)

        }

        obteinedCategory.save()

        jsonResult = getCategory(categoryId)

        jsonResult


    }
}
