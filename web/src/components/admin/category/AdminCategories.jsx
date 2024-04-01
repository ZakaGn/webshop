import './AdminCategories.css'
import React, {useEffect, useState} from 'react'
import CategoryService from 'services/CategoryService'
import {toast} from 'react-toastify'
import CategoryDialog from "components/admin/category/CategoryDialog"

const AdminCategories = () => {
	const [categories, setCategories] = useState([])
	const [isDialogOpen, setIsDialogOpen] = useState(false)
	const [selectedCategory, setSelectedCategory] = useState(null)
	const [searchQuery, setSearchQuery] = useState('')
	let isMounted = true

	useEffect(() => {
		if(!isMounted) return
		fetchCategories()
		return () => {
			isMounted = false
		}
	}, [])

	const fetchCategories = () => {
		CategoryService.fetchCategories().then(data => {
			console.log("admin Categories: ", data)
			setCategories(data)
		}).catch(error => {
			toast.error(error.response?.data?.message || 'Failed to fetch categories')
		})
	}

	const handleSearchChange = (e) => {
		setSearchQuery(e.target.value.toLowerCase())
	}

	const filteredCategories = categories.filter(category =>
		category.name.toLowerCase().includes(searchQuery)
	)

	const handleAddNewCategory = () => {
		setSelectedCategory({name: ''})
		setIsDialogOpen(true)
	}

	const handleEditCategory = (category) => {
		setSelectedCategory(category)
		setIsDialogOpen(true)
	}

	const handleDeleteCategory = async(id) => {
		try{
			await CategoryService.deleteCategory(id)
			fetchCategories()
			toast.success('Category deleted successfully')
		}catch(error){
			toast.error('Failed to delete category')
		}
	}

	const onCategoryFormClose = () => {
		setIsDialogOpen(false)
		setSelectedCategory(null)
		fetchCategories()
	}

	const handleSaveCategory = async(updatedCategory) => {
		console.log("Saving category:", updatedCategory)
		if(updatedCategory.id){
			try{
				await CategoryService.updateCategory(updatedCategory)
				toast.success('Category updated successfully')
			}catch(error){
				if(error.response?.data?.errors){
					for(let field in error.response.data.errors){
						toast.error(error.response.data.errors[field])
					}
				}else{
					toast.error(error.response?.data?.message || 'Failed to update category')
				}
			}
		}else{
			try{
				await CategoryService.addCategory(updatedCategory)
				toast.success('Category added successfully')
			}catch(error){
				if(error.response?.data?.errors){
					for(let field in error.response.data.errors){
						toast.error(error.response.data.errors[field])
					}
				}else{
					toast.error(error.response?.data?.message || 'Failed to add category')
				}
			}
		}
		onCategoryFormClose()
		fetchCategories()
	}

	return (
		<div id="admin-categories">
			<h1>Admin Categories</h1>
			<div className="admin-actions">
				<button onClick={handleAddNewCategory}>Add New Category</button>
				<input
					type="text"
					placeholder="Search categories..."
					onChange={handleSearchChange}
					className="search-input"
				/>
			</div>
			{filteredCategories.map((category, index) => (
				<div className="category-item" key={index} onClick={() => handleEditCategory(category)}>
					{category.name}
				</div>
			))}
			<CategoryDialog
				isOpen={isDialogOpen}
				onClose={onCategoryFormClose}
				category={selectedCategory}
				onSave={handleSaveCategory}
				onDelete={handleDeleteCategory}
			/>
		</div>
	)
}

export default AdminCategories
