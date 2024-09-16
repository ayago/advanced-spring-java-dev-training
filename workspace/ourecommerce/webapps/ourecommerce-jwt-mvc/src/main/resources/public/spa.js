function docReady(fn) {
    // see if DOM is already available
    if (document.readyState === "complete" || document.readyState === "interactive") {
        // call on next available tick
        setTimeout(fn, 1);
    } else {
        document.addEventListener("DOMContentLoaded", fn);
    }
}

let jwtToken = null; // In-memory storage for the JWT
let userRole = null;

docReady(function(){
    document
          .getElementById("login-form")
          .addEventListener("submit", async function (event) {
            event.preventDefault();
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            const response = await fetch("/authenticate", {
              method: "POST",
              headers: {
                "Content-Type": "application/x-www-form-urlencoded",
              },
              body: new URLSearchParams({ username, password }),
            });

            if (response.ok) {
              const data = await response.json();
              jwtToken = response.headers.get("Authorization");
              userRole = data.role;

              if (userRole === "ADMIN") {
                showPage("product-page");
                loadProducts();
              } else if (userRole === "USER") {
                showPage("order-page");
                loadProductsForOrder();
              }
            } else {
              document.getElementById("login-error").style.display = "block";
            }
          });

    document
          .getElementById("product-form")
          .addEventListener("submit", async function (event) {
            event.preventDefault();
            const name = document.getElementById("name").value;
            const description = document.getElementById("description").value;

            await fetch("/api/products", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken,
              },
              body: JSON.stringify({ name, description }),
            });

            loadProducts();
          });

    document
          .getElementById("order-form")
          .addEventListener("submit", async function (event) {
            event.preventDefault();
            const orderRequest = resolveOrderRequest();

            await fetch("/api/orders", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Authorization: jwtToken,
              },
              body: JSON.stringify(orderRequest),
            });

            loadProductsForOrder();
          });

    showPage("login-page");
});

function showPage(pageId) {
  document
    .querySelectorAll(".page")
    .forEach((page) => (page.style.display = "none"));
  document.getElementById(pageId).style.display = "block";
}

async function loadProductsForOrder() {
  const response = await fetchProducts();
  const products = await response.json();
  const select = document.getElementById("product0");

  products.forEach((product) => {
    const option = document.createElement("option");
    option.value = product.productCode;
    option.text = product.name;
    select.add(option);
  });

  storeProductsInSession(products);
}

async function loadProducts() {
  const response = await fetchProducts();
  const products = await response.json();
  const tableBody = document.querySelector("#products-table tbody");

  tableBody.innerHTML = ""; // Clear previous products
  products.forEach((product) => {
    const row = document.createElement("tr");
    row.innerHTML = `
                      <td>${product.name}</td>
                      <td>${product.description}</td>
                      <td><button onclick="deleteProduct(${product.productId})">Delete</button></td>
                  `;
    tableBody.appendChild(row);
  });

  storeProductsInSession(products);
}

async function fetchProducts() {
    return await fetch("/api/products", {
        headers: {
            Authorization: jwtToken,
        },
    });
}

function storeProductsInSession(products) {
  const productsString = JSON.stringify(products);
  sessionStorage.setItem("products", productsString);
}

async function deleteProduct(productId) {
  await fetch(`/api/products/${productId}`, {
    method: "DELETE",
    headers: { Authorization: jwtToken },
  });
  loadProducts();
}

let itemIndex = 1;
function getProductsFromStorage() {
  const productsData = sessionStorage.getItem("products");
  return productsData ? JSON.parse(productsData) : [];
}

function addItem() {
  const container = document.getElementById("order-items-container");
  const products = getProductsFromStorage(); // Fetch products from sessionStorage

  const newItemHtml = `
                    <div id="order_item_${itemIndex}">
                        <label for="product${itemIndex}">Select Product:</label>
                        <select id="product${itemIndex}" name="items[${itemIndex}].productCode">
                            ${products
                              .map(
                                (product) =>
                                  `<option value="${product.productCode}">${product.name}</option>`
                              )
                              .join("")}
                        </select>
                        <label for="count${itemIndex}">Quantity:</label>
                        <input type="number" id="count${itemIndex}" name="items[${itemIndex}].count" value="1">
                        <button type="button" onclick="removeItem(${itemIndex})">Remove</button>
                    </div>
                `;
  container.insertAdjacentHTML("beforeend", newItemHtml);
  itemIndex++;
}

function resolveOrderRequest() {

    const orderItemsDivs = document.querySelectorAll('div[id^="order_item_"]');

    // Create an array to store the items
    const items = [];

    // Loop through each div
    orderItemsDivs.forEach(div => {
        const productId = div.querySelector('select').value;
        const count = div.querySelector('input').value;

        items.push({
            productId: productId,
            count: parseInt(count, 10)
        });
    });

    const orderRequest = {
        items: items
    };

    return orderRequest; // Return the final JSON object
}

function removeItem(index) {
  const orderItem = document.getElementById(`order_item_${index}`);
  orderItem.remove();
}
