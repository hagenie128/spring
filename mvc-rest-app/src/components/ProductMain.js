import { useEffect, useState } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:9999/step08-mvc-rest/api/products';

function ProductMain() {
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({
    name: '',
    price: '',
    category: '',
  });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const fetchProducts = async () => {
    setLoading(true);
    setMessage('');

    try {
      const response = await axios.get(API_URL);
      setProducts(response.data);
    } catch (error) {
      setMessage('상품 목록을 불러오지 못했습니다.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setForm((prevForm) => ({
      ...prevForm,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const product = {
      name: form.name,
      price: Number(form.price),
      category: form.category,
    };

    try {
      const response = await axios.post(API_URL, product);

      setProducts((prevProducts) => [...prevProducts, response.data]);
      setForm({
        name: '',
        price: '',
        category: '',
      });
      setMessage('상품이 등록되었습니다.');
    } catch (error) {
      setMessage('상품 등록에 실패했습니다.');
    }
  };

  return (
    <main className="product-page">
      <section className="product-panel">
        <div className="section-title">
          <p>Product DTO</p>
          <h1>상품 전체보기</h1>
        </div>

        <button className="refresh-button" type="button" onClick={fetchProducts}>
          전체보기
        </button>

        {loading ? (
          <p className="notice">상품 목록을 불러오는 중입니다.</p>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>상품명</th>
                  <th>가격</th>
                  <th>카테고리</th>
                </tr>
              </thead>
              <tbody>
                {products.map((product) => (
                  <tr key={product.id}>
                    <td>{product.id}</td>
                    <td>{product.name}</td>
                    <td>{product.price.toLocaleString()}원</td>
                    <td>{product.category}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>

      <section className="product-panel">
        <div className="section-title">
          <p>Create</p>
          <h2>상품 등록</h2>
        </div>

        <form className="product-form" onSubmit={handleSubmit}>
          <label>
            상품명
            <input
              name="name"
              type="text"
              value={form.name}
              onChange={handleChange}
              placeholder="예: 바닐라라떼"
              required
            />
          </label>

          <label>
            가격
            <input
              name="price"
              type="number"
              min="0"
              value={form.price}
              onChange={handleChange}
              placeholder="예: 5000"
              required
            />
          </label>

          <label>
            카테고리
            <input
              name="category"
              type="text"
              value={form.category}
              onChange={handleChange}
              placeholder="예: 음료"
              required
            />
          </label>

          <button className="submit-button" type="submit">
            등록
          </button>
        </form>

        {message && <p className="notice">{message}</p>}
      </section>
    </main>
  );
}

export default ProductMain;
