document.addEventListener('DOMContentLoaded', function() {
    // Sidebar toggle
    document.getElementById('sidebarCollapse').addEventListener('click', function() {
        document.getElementById('sidebar').classList.toggle('active');
    });

    // Initialize all dropdowns
    var dropdowns = document.querySelectorAll('.dropdown-toggle');
    dropdowns.forEach(function(dropdown) {
        new bootstrap.Dropdown(dropdown);
    });

    // Search functionality
    document.getElementById('searchInput').addEventListener('input', function(e) {
        filterItems();
    });

    // Status filter
    document.getElementById('statusFilter').addEventListener('change', function(e) {
        filterItems();
    });

    // Category filter
    document.getElementById('categoryFilter').addEventListener('change', function(e) {
        filterItems();
    });

    // Sort functionality
    document.getElementById('sortBy').addEventListener('change', function(e) {
        sortItems(e.target.value);
    });

    // Image preview
    document.getElementById('itemImage').addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const preview = document.getElementById('imagePreview');
                preview.innerHTML = `<img src="${e.target.result}" class="img-thumbnail" style="max-height: 150px">`;
            }
            reader.readAsDataURL(file);
        }
    });

    // Handle responsive behavior
    function handleResize() {
        if (window.innerWidth <= 768) {
            document.getElementById('sidebar').classList.add('active');
        } else {
            document.getElementById('sidebar').classList.remove('active');
        }
    }

    window.addEventListener('resize', handleResize);
    handleResize();
});

// Item Modal Functions
let isEditing = false;
let currentRow = null;

function editItem(button) {
    isEditing = true;
    currentRow = button.closest('tr');
    const modal = new bootstrap.Modal(document.getElementById('itemModal'));

    // Get data from the table row
    const cells = currentRow.cells;
    document.getElementById('itemCode').value = cells[0].textContent;
    document.getElementById('itemName').value = cells[2].textContent;
    document.getElementById('description').value = cells[3].textContent;
    document.getElementById('price').value = parseFloat(cells[4].textContent);
    document.getElementById('category').value = cells[5].textContent.toLowerCase();
    document.getElementById('userEmail').value = cells[6].textContent;
    document.getElementById('status').value = cells[7].querySelector('select').value;

    // Show current image
    const imagePreview = document.getElementById('imagePreview');
    const currentImage = cells[1].querySelector('img').src;
    imagePreview.innerHTML = `<img src="${currentImage}" class="img-thumbnail" style="max-height: 150px">`;

    // Update modal title
    document.getElementById('modalTitle').textContent = 'Edit Item';

    modal.show();
}

function saveItem() {
    // Get form values
    const code = document.getElementById('itemCode').value;
    const name = document.getElementById('itemName').value;
    const description = document.getElementById('description').value;
    const price = document.getElementById('price').value;
    const category = document.getElementById('category').value;
    const email = document.getElementById('userEmail').value;
    const status = document.getElementById('status').value;
    const imageInput = document.getElementById('itemImage');

    // Basic validation
    if (!code || !name || !description || !price || !category || !email || !status) {
        alert('Please fill in all required fields');
        return;
    }

    // Get image URL (either from file input or existing image)
    let imageUrl = 'https://via.placeholder.com/50';
    if (imageInput.files && imageInput.files[0]) {
        imageUrl = URL.createObjectURL(imageInput.files[0]);
    } else if (isEditing) {
        imageUrl = currentRow.cells[1].querySelector('img').src;
    }

    if (isEditing) {
        // Update existing row
        const cells = currentRow.cells;
        cells[0].textContent = code;
        cells[1].querySelector('img').src = imageUrl;
        cells[2].textContent = name;
        cells[3].textContent = description;
        cells[4].textContent = parseFloat(price).toFixed(2);
        cells[5].textContent = category.charAt(0).toUpperCase() + category.slice(1);
        cells[6].textContent = email;
        cells[7].querySelector('select').value = status;
    } else {
        // Add new row
        const table = document.querySelector('table tbody');
        const newRow = table.insertRow();
        newRow.innerHTML = `
            <td>${code}</td>
            <td><img src="${imageUrl}" alt="Item" class="img-thumbnail"></td>
            <td>${name}</td>
            <td>${description}</td>
            <td>${parseFloat(price).toFixed(2)}</td>
            <td>${category.charAt(0).toUpperCase() + category.slice(1)}</td>
            <td>${email}</td>
            <td>
                <select class="form-select form-select-sm status-select" onchange="updateStatus(this)">
                    <option value="pending" ${status === 'pending' ? 'selected' : ''}>Pending</option>
                    <option value="approved" ${status === 'approved' ? 'selected' : ''}>Approved</option>
                    <option value="disapproved" ${status === 'disapproved' ? 'selected' : ''}>Disapproved</option>
                    <option value="onbid" ${status === 'onbid' ? 'selected' : ''}>On Bid</option>
                    <option value="soldout" ${status === 'soldout' ? 'selected' : ''}>Sold Out</option>
                </select>
            </td>
            <td>
                <button class="btn btn-sm btn-outline-primary me-1" onclick="editItem(this)">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteItem(this)">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
    }

    // Reset form and close modal
    document.getElementById('itemForm').reset();
    document.getElementById('imagePreview').innerHTML = '';
    bootstrap.Modal.getInstance(document.getElementById('itemModal')).hide();
    isEditing = false;
    currentRow = null;
}

function updateStatus(select) {
    // Add any additional logic needed when status changes
    console.log(`Status updated to: ${select.value}`);
}

function deleteItem(button) {
    if (confirm('Are you sure you want to delete this item?')) {
        button.closest('tr').remove();
    }
}

function filterItems() {
    const searchText = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;
    const categoryFilter = document.getElementById('categoryFilter').value;
    const rows = document.getElementById('itemTableBody').getElementsByTagName('tr');

    for (let row of rows) {
        const code = row.cells[0].textContent.toLowerCase();
        const name = row.cells[2].textContent.toLowerCase();
        const description = row.cells[3].textContent.toLowerCase();
        const category = row.cells[5].textContent.toLowerCase();
        const status = row.cells[7].querySelector('select').value;

        const matchesSearch = code.includes(searchText) ||
            name.includes(searchText) ||
            description.includes(searchText);

        const matchesStatus = !statusFilter || status === statusFilter;
        const matchesCategory = !categoryFilter || category === categoryFilter;

        row.style.display = matchesSearch && matchesStatus && matchesCategory ? '' : 'none';
    }
}

function sortItems(criteria) {
    const tbody = document.getElementById('itemTableBody');
    const rows = Array.from(tbody.getElementsByTagName('tr'));

    rows.sort((a, b) => {
        let aValue, bValue;

        switch(criteria) {
            case 'code':
                aValue = a.cells[0].textContent;
                bValue = b.cells[0].textContent;
                break;
            case 'name':
                aValue = a.cells[2].textContent;
                bValue = b.cells[2].textContent;
                break;
            case 'price':
                aValue = parseFloat(a.cells[4].textContent);
                bValue = parseFloat(b.cells[4].textContent);
                return aValue - bValue;
            case 'status':
                aValue = a.cells[7].querySelector('select').value;
                bValue = b.cells[7].querySelector('select').value;
                break;
        }

        if (criteria === 'price') return 0; // Already handled in case
        return aValue.localeCompare(bValue);
    });

    // Clear and re-append sorted rows
    rows.forEach(row => tbody.appendChild(row));
}

// Reset modal state when it's closed
document.getElementById('itemModal').addEventListener('hidden.bs.modal', function () {
    document.getElementById('itemForm').reset();
    document.getElementById('imagePreview').innerHTML = '';
    document.getElementById('modalTitle').textContent = 'Add New Item';
    isEditing = false;
    currentRow = null;
});