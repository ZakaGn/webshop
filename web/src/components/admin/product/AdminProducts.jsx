import './AdminProducts.css'
import React, {useEffect, useState} from 'react'
import {ProductService} from 'services/ProductService'
import {categoryService} from 'services/CategoryService'
import {toast} from 'react-toastify'
import ProductDialog from "components/admin/product/ProductDialog"

const AdminProducts = () => {
	const [products, setProducts] = useState([])
	const [categories, setCategories] = useState([])
	const [isDialogOpen, setIsDialogOpen] = useState(false)
	const [selectedProduct, setSelectedProduct] = useState(null)
	const [searchQuery, setSearchQuery] = useState('')
	let isMounted = true

	useEffect(() => {
		if(!isMounted) return
		fetchProducts()
		fetchCategories()
		return () => {
			isMounted = false
		}
	}, [])

	const fetchProducts = () => {
		ProductService.fetchProducts().then(data => {
			setProducts(data)
		}).catch(error => {
			toast.error(error.response?.data?.message || 'Failed to fetch products')
		})
	}

	const fetchCategories = () => {
		categoryService.fetchCategories().then(data => {
			setCategories(data)
		}).catch(error => {
			toast.error(error.response?.data?.message || 'Failed to fetch categories')
		})
	}

	const handleSearchChange = (e) => {
		setSearchQuery(e.target.value.toLowerCase())
	}

	const filteredProducts = products.filter(product =>
		product.name.toLowerCase().includes(searchQuery)
	)

	const handleAddNewProduct = () => {
		setSelectedProduct({name: '', categoryId: ''})
		setIsDialogOpen(true)
	}

	const handleEditProduct = (product) => {
		setSelectedProduct(product)
		setIsDialogOpen(true)
	}

	const handleDeleteProduct = async(id) => {
		try{
			await ProductService.deleteProduct(id)
			fetchProducts()
			toast.success('Product deleted successfully')
		}catch(error){
			toast.error('Failed to delete product')
		}
	}

	const onProductFormClose = () => {
		setIsDialogOpen(false)
		setSelectedProduct(null)
		fetchProducts()
	}

	const handleSaveProduct = async(updatedProduct) => {
		if(updatedProduct.id){
			try{
				await ProductService.updateProduct(updatedProduct)
				toast.success('Product updated successfully')
			}catch(error){
				toast.error('Failed to update product')
			}
		}else{
			try{
				await ProductService.addProduct(updatedProduct)
				toast.success('Product added successfully')
			}catch(error){
				toast.error('Failed to add product')
			}
		}
		onProductFormClose()
		fetchProducts()
	}

	return (
		<div id="admin-products">
			<h1>Admin Products</h1>
			<div className="admin-actions">
				<button onClick={handleAddNewProduct}>Add New Product</button>
				<input
					type="text"
					placeholder="Search products..."
					onChange={handleSearchChange}
					className="search-input"
				/>
			</div>
			{filteredProducts.map((product, index) => (
				<div className="product-item" key={index} onClick={() => handleEditProduct(product)}>
					{product.name} - Category: {categories.find(c => c.id === product.categoryId)?.name || 'Unknown'}
				</div>
			))}
			<ProductDialog
				isOpen={isDialogOpen}
				onClose={onProductFormClose}
				product={selectedProduct}
				categories={categories}
				onSave={handleSaveProduct}
				onDelete={handleDeleteProduct}
			/>
		</div>
	)
}

export default AdminProducts
