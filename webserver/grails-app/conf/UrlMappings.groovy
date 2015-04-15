class UrlMappings {

	static mappings = {

		"/$categoryId?" {
            controller = "Category"
            action = [GET: 'getCategory', POST:'addCategory',PUT:'putCategory' ,DELETE: 'notAllowed']
        }

	}
}
