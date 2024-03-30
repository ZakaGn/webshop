import './Home.css'
import React, {useState, useEffect} from 'react'
import CategoriesSidebar from 'components/layout/CategoriesSidebar'
import ProductCard from 'components/product/ProductCard'
import ProductDetailDialog from 'components/product/ProductDetailDialog'
import PaginationComponent from './PaginationComponent'
import SearchComponent from './SearchComponent'
import ProductService from 'services/ProductService'
import CategoryService from 'services/CategoryService'

const Home = () => {
	const [products, setProducts] = useState([])
	const [categories, setCategories] = useState([])
	const [selectedProduct, setSelectedProduct] = useState(null)
	const [currentPage, setCurrentPage] = useState(1)
	const [productsPerPage] = useState(10)
	const [searchTerm, setSearchTerm] = useState('')
	let isMounted = true

	useEffect(() => {
		if(!isMounted) return
		fetchCategories()
		fetchProducts()
		return () => {
			isMounted = false
		}
	}, [])

	const fetchCategories = async() => {
		const data = await CategoryService.fetchCategories()
		setCategories(data)
	}

	const fetchProducts = async() => {
		const data = await ProductService.fetchProducts()
		setProducts(data)
	}

	const handleSearch = (searchTerm) => {
		setSearchTerm(searchTerm.toLowerCase())
	}

	const indexOfLastProduct = currentPage*productsPerPage
	const indexOfFirstProduct = indexOfLastProduct - productsPerPage
	const currentProducts = products.slice(indexOfFirstProduct, indexOfLastProduct)

	const paginate = (pageNumber) => setCurrentPage(pageNumber)

	const handleProductClick = (product) => {
		setSelectedProduct(product)
	}

	const handleAddToCart = (product) => {
		console.log('Add to cart:', product)
	}

	return (
		<div className="home-container">
			<CategoriesSidebar categories={categories}/>
			<div className="main-content">
				<SearchComponent onSearch={handleSearch}/>
				<div className="product-list">
					{currentProducts.filter(product => product.name.toLowerCase().includes(searchTerm)).map(product => (
						<ProductCard key={product.id} product={product} onClick={handleProductClick}/>
					))}
				</div>
				<PaginationComponent
					productsPerPage={productsPerPage}
					totalProducts={products.length}
					paginate={paginate}
				/>
				{selectedProduct &&
					<ProductDetailDialog
						product={selectedProduct}
						onClose={() => setSelectedProduct(null)}
						onAddToCart={handleAddToCart}
					/>
				}
			</div>
		</div>
	)
}

export default Home
