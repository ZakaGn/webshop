import './Home.css'
import React, {useState, useEffect} from 'react'
import CategoriesSidebar from 'components/layout/CategoriesSidebar'
import ProductCard from 'components/product/ProductCard'
import ProductDetailDialog from 'components/product/ProductDetailDialog'
import PaginationComponent from './PaginationComponent'
import SearchComponent from './SearchComponent'
import ProductService from 'services/ProductService'
import CategoryService from 'services/CategoryService'

const Home = ({cart, setCart}) => {
	const [allProducts, setAllProducts] = useState([])
	const [filteredProducts, setFilteredProducts] = useState([])
	const [categories, setCategories] = useState([])
	const [selectedProduct, setSelectedProduct] = useState(null)
	const [selectedCategory, setSelectedCategory] = useState(null)
	const [currentPage, setCurrentPage] = useState(1)
	const [productsPerPage, setProductsPerPage] = useState(10)
	const [searchTerm, setSearchTerm] = useState('')
	const [isLoading, setIsLoading] = useState(false)
	let isMounted = true
	let fetchedCategories = []
	let fetchedProducts = []

	useEffect(() => {
		if(!isMounted) return
		fetchInitialData().then(() => {
				setCategories(fetchedCategories)
				setAllProducts(fetchedProducts)
				setFilteredProducts(fetchedProducts)
				setIsLoading(false)
			}
		)
		return () => {isMounted = false}
	}, [])

	useEffect(() => {
		const newFilteredProducts = allProducts.filter(product => {
			return (!selectedCategory || product.categoryId === selectedCategory.id) &&
				product.name.toLowerCase().includes(searchTerm)
		})
		setFilteredProducts(newFilteredProducts)
	}, [searchTerm, selectedCategory, allProducts])

	const fetchInitialData = async() => {
		setIsLoading(true)
		fetchedCategories = await CategoryService.fetchCategories()
		fetchedProducts = await ProductService.fetchProducts()
	}

	const handleCategorySelect = (category) => {
		setSelectedCategory(category)
		setCurrentPage(1)
	}

	const handleSearch = (term, isSearching) => {
		setIsLoading(isSearching)
		setSearchTerm(term.toLowerCase())
		setCurrentPage(1)
	}

	const indexOfLastProduct = currentPage*productsPerPage
	const indexOfFirstProduct = indexOfLastProduct - productsPerPage
	const currentProducts = filteredProducts.slice(indexOfFirstProduct, indexOfLastProduct)

	const paginate = (pageNumber) => {
		if(pageNumber >= 1 && pageNumber <= totalPages){
			setCurrentPage(pageNumber)
		}
	}
	const totalPages = Math.ceil(filteredProducts.length/productsPerPage)

	const handleProductsPerPageChange = (event) => {
		setProductsPerPage(Number(event.target.value))
		setCurrentPage(1)
	}

	return (
		<div id="home-container" className="home-container">
			<CategoriesSidebar categories={categories} onCategorySelect={handleCategorySelect}/>
			<div className="main-content">
				<div className="search-wrapper">
					<SearchComponent onSearch={handleSearch}/>
					<div className="products-per-page-selector">
						<label htmlFor="products-per-page">Products per page:</label>
						<select
							id="products-per-page"
							value={productsPerPage}
							onChange={handleProductsPerPageChange}
						>
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
						</select>
					</div>
				</div>
				{isLoading ? (
					<div>Loading products...</div>
				) : currentProducts.length > 0 ? (
					<div className="product-list">
						{currentProducts.map(product => (
							<ProductCard key={product.id} product={product} onClick={() => setSelectedProduct(product)}/>
						))}
					</div>
				) : (
					<div>No products found.</div>
				)}
				<PaginationComponent
					totalPages={totalPages}
					currentPage={currentPage}
					onPageChange={paginate}
				/>
				{selectedProduct && (
					<ProductDetailDialog
						cart={cart}
						product={selectedProduct}
						onClose={() => setSelectedProduct(null)}
						setCart={setCart}
					/>
				)}
			</div>
		</div>
	)
}

export default Home
