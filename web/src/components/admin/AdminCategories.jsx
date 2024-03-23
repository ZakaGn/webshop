import React, {useEffect, useState} from 'react'
import {categoryService} from 'services/CategoryService'
import {toast} from 'react-toastify'
import AddCategoryForm from 'components/category/AddCategoryForm'
import EditCategoryForm from 'components/category/EditCategoryForm'

const AdminCategories = () => {
	const [categories, setCategories] = useState([])
	const [showAddForm, setShowAddForm] = useState(false)
	const [currentCategory, setCurrentCategory] = useState(null)

	useEffect(() => {
		fetchCategories()
	}, [])

	const fetchCategories = async() => {
		try{
			const {data} = await categoryService.fetchCategories()
			console.log("data:", data)
			setCategories(data)
		}catch(error){
			const errorMessage = error.response?.data?.message || 'Failed to fetch categories'
			toast.error(errorMessage)
		}
	}

	const handleAddNewCategory = (category) => {
		setShowAddForm(true)
	}

	const handleEditCategory = (category) => {
		setCurrentCategory(category)
	}

	const handleDeleteCategory = async(categoryId) => {
		try{
			await categoryService.deleteCategory(categoryId)
			await fetchCategories()
			toast.success('Category deleted successfully')
		}catch(error){
			const errorMessage = error.response?.data?.message || 'Failed to delete category'
			toast.error(errorMessage)
		}
	}

	const onCategoryFormClose = () => {
		setShowAddForm(false)
		setCurrentCategory(null)
		fetchCategories()
	}

	return (
		<div>
			<h1>Admin Categories</h1>
			<button onClick={handleAddNewCategory}>Add New Category</button>
			{categories?.map((category) => (
				<div key={category.id}>
					{category.name}
					<button onClick={() => handleEditCategory(category)}>Edit</button>
					<button onClick={() => handleDeleteCategory(category.id)}>Delete</button>
				</div>
			))}
			{showAddForm && <AddCategoryForm onClose={onCategoryFormClose}/>}
			{currentCategory && (
				<EditCategoryForm category={currentCategory} onClose={onCategoryFormClose}/>
			)}
		</div>
	)
}

export default AdminCategories
