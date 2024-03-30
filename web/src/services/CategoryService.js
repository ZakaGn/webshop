import api from 'utils/api'

const CategoryService = {
	fetchCategories: async() => api.fetchCategories(),
	addCategory: async(category) => api.addCategory(category),
	updateCategory: async(category) => api.updateCategory(category),
	deleteCategory: async(id) => api.deleteCategory(id)
}

export default CategoryService
