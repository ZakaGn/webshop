import React from 'react'
import './PaginationComponent.css'

const PaginationComponent = ({totalPages, currentPage, onPageChange}) => {
	const isFirstPage = currentPage === 1
	const isLastPage = currentPage === totalPages

	return (
		<div className="pagination-container">
			<button
				className={`pagination-button ${isFirstPage ? 'disabled' : ''}`}
				onClick={() => !isFirstPage && onPageChange(currentPage - 1)}
				disabled={isFirstPage}
			>
				Previous
			</button>
			{[...Array(totalPages).keys()].map(n => {
				const page = n + 1
				return (
					<button
						key={page}
						className={`pagination-button ${page === currentPage ? 'active' : ''}`}
						onClick={() => onPageChange(page)}
					>
						{page}
					</button>
				)
			})}
			<button
				className={`pagination-button ${isLastPage ? 'disabled' : ''}`}
				onClick={() => !isLastPage && onPageChange(currentPage + 1)}
				disabled={isLastPage}
			>
				Next
			</button>
		</div>
	)
}

export default PaginationComponent
