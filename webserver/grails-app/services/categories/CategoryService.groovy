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

        Map jsonResult              = [:]
        def jsonChildren            = []
        def resultParentCategory    = []

        if (!categoryId){
            throw  new NotFoundException("You must provider category_id")
        }

        def category = Category.findByCategoryId(categoryId)

        if (!category){
            throw  new NotFoundException("The categoryId not found")
        }

        def childrenCategories = Category.findAllByParentCategoryId(categoryId,[ sort: "name", order: "asc"])
        childrenCategories.each{
            jsonChildren.add(
                    categoryId  : it.categoryId,
                    name        : it.name,
                    status      : it.status
            )
        }


        resultParentCategory = getParentCategory(category.parentCategoryId)

        jsonResult.category_id          = category.categoryId
        jsonResult.name                 = category.name
        jsonResult.parent_category      = resultParentCategory
        jsonResult.children_categories  = jsonChildren


        jsonResult

    }



    def createCategory(def parentCategoryId,def jsonCategory){

        Map jsonResult = [:]
        def responseMenssage = ''

        if (!Category.findByCategoryId(parentCategoryId)){

            throw  new NotFoundException("The categoryId = "+parentCategoryId+" not found")

        }

        def newCategory =  new Category(

                categoryId          : jsonCategory?.category_id,
                parentCategoryId    : parentCategoryId,
                name                : jsonCategory?.name
        )

        if(!newCategory.validate()) {
            newCategory.errors.allErrors.each {
                responseMenssage += MessageFormat.format(it.defaultMessage, it.arguments) + " "
            }

            throw new BadRequestException(responseMenssage)

        }

        newCategory.save()

        jsonResult.category_id       = newCategory.categoryId
        jsonResult.name              = newCategory.name
        jsonResult.parent_category   = newCategory.parentCategoryId

        jsonResult


    }

    def createCategory(def jsonCategory){

        Map jsonResult = [:]
        def responseMenssage = ''

        def newCategory = new Category(
                categoryId  : jsonCategory?.category_id,
                name        : jsonCategory?.name
        )

        if(!newCategory.validate()){
            newCategory.errors.allErrors.each{
                responseMenssage+=MessageFormat.format(it.defaultMessage,it.arguments) + " "
            }
            throw new BadRequestException(responseMenssage)
        }

        newCategory.save()

        jsonResult.category_id       = newCategory.categoryId
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

        def obteinedCategory = Category.findByCategoryId(categoryId)

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


    def getParentCategory(def parentCategoryId){

        def resultParentLocations = []

        while (parentCategoryId){
            def parent = getParent(parentCategoryId)

            resultParentLocations.add(

                    category_id : parent.category_id,
                    name        : parent.name,
                    status      : parent.status

            )
            parentCategoryId = parent.parent_category_id
        }

        resultParentLocations


    }

    def getParent(def parentCategoryId){

        def jsonParent = [:]

        if (parentCategoryId){

            def parentCategory = Category.findByCategoryId(parentCategoryId)

            jsonParent.category_id          = parentCategory.categoryId
            jsonParent.name                 = parentCategory.name
            jsonParent.status               = parentCategory.status
            jsonParent.parent_category_id   = parentCategory.parentCategoryId
        }

        jsonParent
    }
}
