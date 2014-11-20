import categories.Category

class BootStrap {

    def init = { servletContext ->
        test{

        }
        development{

            if (Category.count() == 0){
                def category01 = new Category(
                        categoryId: "MB1",
                        name:"Generos musicales"
                )

                category01.save()

                def category02 = new Category(
                        categoryId: "MB11",
                        name:"Mariachi",
                        parentCategoryId: category01.categoryId
                )

                category02.save()

                def category03 = new Category(
                        categoryId: "MB12",
                        name: "Norteño",
                        parentCategoryId: category01.categoryId
                )
                category03.save()
            }

        }
        production{

        }
    }
    def destroy = {
    }
}
