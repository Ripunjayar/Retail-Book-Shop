import { useMemo, useState } from 'react'
import './App.css'

const books = [
  {
    id: 1,
    title: 'Atomic Habits',
    author: 'James Clear',
    category: 'Self Improvement',
    price: 18.99,
    rating: 4.9,
    badge: 'Bestseller',
    accent: 'linear-gradient(135deg, #f59e0b, #f97316)',
  },
  {
    id: 2,
    title: 'The Midnight Library',
    author: 'Matt Haig',
    category: 'Fiction',
    price: 16.5,
    rating: 4.8,
    badge: "Editor's pick",
    accent: 'linear-gradient(135deg, #8b5cf6, #ec4899)',
  },
  {
    id: 3,
    title: 'Deep Work',
    author: 'Cal Newport',
    category: 'Business',
    price: 20.0,
    rating: 4.7,
    badge: 'Trending',
    accent: 'linear-gradient(135deg, #0ea5e9, #2563eb)',
  },
  {
    id: 4,
    title: 'The Silent Patient',
    author: 'Alex Michaelides',
    category: 'Thriller',
    price: 17.75,
    rating: 4.6,
    badge: 'New release',
    accent: 'linear-gradient(135deg, #ef4444, #7c2d12)',
  },
  {
    id: 5,
    title: 'The Book of Forest',
    author: 'M. R. Swan',
    category: 'Fantasy',
    price: 22.0,
    rating: 4.9,
    badge: 'Limited',
    accent: 'linear-gradient(135deg, #10b981, #0f766e)',
  },
  {
    id: 6,
    title: 'A Brief History of Time',
    author: 'Stephen Hawking',
    category: 'Science',
    price: 19.25,
    rating: 4.8,
    badge: 'Classic',
    accent: 'linear-gradient(135deg, #6366f1, #312e81)',
  },
]

const categories = ['All', 'Fiction', 'Fantasy', 'Business', 'Self Improvement', 'Science', 'Thriller']

function App() {
  const [selectedCategory, setSelectedCategory] = useState('All')
  const [cart, setCart] = useState([])

  const visibleBooks = useMemo(() => {
    if (selectedCategory === 'All') {
      return books
    }

    return books.filter((book) => book.category === selectedCategory)
  }, [selectedCategory])

  const addToCart = (book) => {
    setCart((currentCart) => {
      const existingItem = currentCart.find((item) => item.id === book.id)

      if (existingItem) {
        return currentCart.map((item) =>
          item.id === book.id ? { ...item, quantity: item.quantity + 1 } : item,
        )
      }

      return [...currentCart, { ...book, quantity: 1 }]
    })
  }

  const updateQuantity = (id, change) => {
    setCart((currentCart) =>
      currentCart
        .map((item) =>
          item.id === id ? { ...item, quantity: item.quantity + change } : item,
        )
        .filter((item) => item.quantity > 0),
    )
  }

  const subtotal = cart.reduce((total, item) => total + item.price * item.quantity, 0)
  const itemCount = cart.reduce((total, item) => total + item.quantity, 0)

  return (
    <div className="bookshop-shell">
      <header className="topbar">
        <div className="brand-wrap">
          <div className="brand-mark">C</div>
          <div>
            <p className="brand-name">Chapter & Co.</p>
            <p className="brand-tag">Independent bookshop</p>
          </div>
        </div>

        <nav className="main-nav" aria-label="Primary navigation">
          <a href="#">Home</a>
          <a href="#">New arrivals</a>
          <a href="#">Bestsellers</a>
          <a href="#">Gift cards</a>
        </nav>

        <button type="button" className="nav-action">
          Cart ({itemCount})
        </button>
      </header>

      <main className="shop-layout">
        <section className="hero-panel">
          <div className="hero-copy">
            <p className="eyebrow">Curated for curious minds</p>
            <h1>Find your next favourite read.</h1>
            <p className="hero-text">
              Discover handpicked novels, practical guides, and timeless classics for every kind of reader.
            </p>

            <div className="hero-actions">
              <button type="button" className="primary-btn">Shop bestsellers</button>
              <button type="button" className="secondary-btn">Explore genres</button>
            </div>

            <div className="stats-row" aria-label="Store statistics">
              <div>
                <strong>12k+</strong>
                <span>Readers</span>
              </div>
              <div>
                <strong>4.9/5</strong>
                <span>Rating</span>
              </div>
              <div>
                <strong>24h</strong>
                <span>Dispatch</span>
              </div>
            </div>
          </div>

          <div className="hero-spotlight" aria-label="Featured book">
            <div className="spotlight-book">
              <span className="spotlight-tag">Featured</span>
              <div className="cover-art cover-art--spotlight">THE CROWN</div>
              <div className="book-meta">
                <p>THE KINGDOM OF WORDS</p>
                <span>by Eliza Rowan</span>
              </div>
              <div className="spotlight-footer">
                <strong>$24.00</strong>
                <button type="button">Add to cart</button>
              </div>
            </div>
          </div>
        </section>

        <section className="catalog-panel">
          <div className="catalog-header">
            <div>
              <p className="eyebrow">Browse collection</p>
              <h2>Popular picks</h2>
            </div>

            <div className="filter-group" aria-label="Book categories">
              {categories.map((category) => (
                <button
                  key={category}
                  type="button"
                  className={selectedCategory === category ? 'filter active' : 'filter'}
                  onClick={() => setSelectedCategory(category)}
                >
                  {category}
                </button>
              ))}
            </div>
          </div>

          <div className="book-grid">
            {visibleBooks.map((book) => (
              <article key={book.id} className="book-card">
                <div className="book-cover" style={{ background: book.accent }}>
                  <span>{book.badge}</span>
                </div>

                <div className="book-card-body">
                  <div className="book-heading">
                    <div>
                      <p className="book-category">{book.category}</p>
                      <h3>{book.title}</h3>
                    </div>
                    <span className="rating">★ {book.rating}</span>
                  </div>

                  <p className="author">{book.author}</p>

                  <div className="book-footer">
                    <strong>${book.price.toFixed(2)}</strong>
                    <button type="button" onClick={() => addToCart(book)}>
                      Add to cart
                    </button>
                  </div>
                </div>
              </article>
            ))}
          </div>
        </section>

        <aside className="cart-panel" aria-label="Shopping cart summary">
          <div className="cart-header">
            <div>
              <p className="eyebrow">Your basket</p>
              <h3>Cart summary</h3>
            </div>
            <span>{itemCount} items</span>
          </div>

          {cart.length === 0 ? (
            <div className="empty-cart">
              <p>Your cart is empty.</p>
              <span>Add a few titles to get started.</span>
            </div>
          ) : (
            <div className="cart-items">
              {cart.map((item) => (
                <div key={item.id} className="cart-item">
                  <div className="mini-cover" style={{ background: item.accent }}>
                    {item.title.slice(0, 2).toUpperCase()}
                  </div>

                  <div className="item-details">
                    <p>{item.title}</p>
                    <span>${item.price.toFixed(2)}</span>
                  </div>

                  <div className="qty-controls" aria-label={`Adjust quantity for ${item.title}`}>
                    <button type="button" onClick={() => updateQuantity(item.id, -1)}>
                      −
                    </button>
                    <span>{item.quantity}</span>
                    <button type="button" onClick={() => updateQuantity(item.id, 1)}>
                      +
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}

          <div className="totals">
            <div>
              <span>Subtotal</span>
              <strong>${subtotal.toFixed(2)}</strong>
            </div>
            <div>
              <span>Shipping</span>
              <strong>Free</strong>
            </div>
            <div className="grand-total">
              <span>Total</span>
              <strong>${subtotal.toFixed(2)}</strong>
            </div>
          </div>

          <button type="button" className="checkout-btn">
            Proceed to checkout
          </button>
        </aside>
      </main>
    </div>
  )
}

export default App
