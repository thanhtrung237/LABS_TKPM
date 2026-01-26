// JavaScript cho Design Patterns Web Demo với Email Notifications

const API_BASE = '/api/patterns';

// Email subscribers management
let subscribers = [];

// Utility functions
function showLoading() {
    document.getElementById('loading').style.display = 'block';
}

function hideLoading() {
    document.getElementById('loading').style.display = 'none';
}

function showResult(elementId, content, isSuccess = true) {
    const resultElement = document.getElementById(elementId);
    resultElement.textContent = content;
    resultElement.className = `result ${isSuccess ? 'success' : 'error'}`;
    resultElement.style.display = 'block';
}

function clearResult(elementId) {
    const resultElement = document.getElementById(elementId);
    resultElement.textContent = '';
    resultElement.className = 'result';
    resultElement.style.display = 'none';
}

async function makeRequest(url, method = 'GET', data = null) {
    showLoading();
    try {
        const options = {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            }
        };
        
        if (data) {
            options.body = JSON.stringify(data);
        }
        
        const response = await fetch(url, options);
        const result = await response.json();
        
        hideLoading();
        return { success: response.ok, data: result };
    } catch (error) {
        hideLoading();
        return { success: false, error: error.message };
    }
}

// Email Subscriber Management Functions
async function addSubscriber() {
    const email = document.getElementById('subscriberEmail').value;
    const name = document.getElementById('subscriberName').value;
    
    if (!email || !name) {
        showResult('observer-result', '❌ Vui lòng nhập email và tên!', false);
        return;
    }
    
    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        showResult('observer-result', '❌ Email không hợp lệ!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/observer/add-subscriber`, 'POST', {
        email: email,
        name: name
    });
    
    if (result.success) {
        const data = result.data;
        const output = `✅ Email Subscriber Added Successfully!

📧 Email: ${data.subscriber.email}
👤 Name: ${data.subscriber.name}
📊 Total Subscribers: ${data.totalSubscribers}

🎯 Observer Pattern Setup:
• EmailSubscriber được thêm vào hệ thống
• Sẽ nhận email khi có thay đổi giá cổ phiếu hoặc task status
• Pattern: Subject → notify → EmailSubscriber → send email

💡 Bây giờ hãy test cập nhật giá hoặc task status để xem email notifications!`;
        
        showResult('observer-result', output, true);
        
        // Clear input fields
        document.getElementById('subscriberEmail').value = '';
        document.getElementById('subscriberName').value = '';
        
        // Refresh subscribers list if visible
        if (document.getElementById('subscribers-list').style.display !== 'none') {
            viewSubscribers();
        }
    } else {
        showResult('observer-result', `❌ Error: ${result.data.message || result.error}`, false);
    }
}

async function viewSubscribers() {
    const result = await makeRequest(`${API_BASE}/observer/subscribers`, 'GET');
    
    if (result.success) {
        const data = result.data;
        const subscribersList = document.getElementById('subscribers-list');
        const subscribersContent = document.getElementById('subscribers-content');
        
        if (data.subscribers.length === 0) {
            subscribersContent.innerHTML = '<p style="color: #6c757d; font-style: italic;">Chưa có subscribers nào. Hãy thêm email để nhận thông báo!</p>';
        } else {
            subscribersContent.innerHTML = data.subscribers.map(subscriber => `
                <div class="subscriber-item">
                    <span>📧 ${subscriber.name} &lt;${subscriber.email}&gt;</span>
                    <button class="remove-btn" onclick="removeSubscriber('${subscriber.email}')">❌ Remove</button>
                </div>
            `).join('');
        }
        
        subscribersList.style.display = 'block';
        
        const output = `📋 Email Subscribers List

📊 Total Subscribers: ${data.totalCount}
📧 Ready to receive notifications for:
• Stock price changes
• Task status updates

🎯 Observer Pattern Active:
${data.totalCount > 0 ? 
    '✅ Subscribers registered and ready for notifications' : 
    '⚠️ No subscribers yet - add emails to see Observer Pattern in action'}`;
        
        showResult('observer-result', output, data.totalCount > 0);
    } else {
        showResult('observer-result', `❌ Error: ${result.error}`, false);
    }
}

async function removeSubscriber(email) {
    const result = await makeRequest(`${API_BASE}/observer/remove-subscriber/${encodeURIComponent(email)}`, 'DELETE');
    
    if (result.success) {
        showResult('observer-result', `✅ Removed subscriber: ${email}`, true);
        viewSubscribers(); // Refresh the list
    } else {
        showResult('observer-result', `❌ Error removing subscriber: ${result.data.message}`, false);
    }
}

// Composite Pattern Functions
async function createFileSystem() {
    const result = await makeRequest(`${API_BASE}/composite/filesystem`, 'POST', {});
    
    if (result.success) {
        const data = result.data;
        const output = `✅ File System Created Successfully!

📁 Root Directory: ${data.name}
📊 Total Size: ${data.totalSize} bytes
📂 Child Count: ${data.childCount}

Structure:
- 📄 README.md (16 bytes)
- 📄 config.json (17 bytes)  
- 📁 src/
  - 📄 Main.java (20 bytes)

🎯 Pattern: Composite Pattern
- Directory có thể chứa File và Directory khác
- Tính tổng size của tất cả children
- Xử lý thống nhất File và Directory`;
        
        showResult('composite-result', output, true);
    } else {
        showResult('composite-result', `❌ Error: ${result.error || 'Failed to create file system'}`, false);
    }
}

async function addFile() {
    const fileName = document.getElementById('fileName').value;
    const fileContent = document.getElementById('fileContent').value;
    
    if (!fileName || !fileContent) {
        showResult('composite-result', '❌ Vui lòng nhập tên file và nội dung!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/composite/add-file`, 'POST', {
        fileName: fileName,
        content: fileContent
    });
    
    if (result.success) {
        const data = result.data;
        const output = `✅ File Added Successfully!

📄 File Name: ${data.fileName}
📊 File Size: ${data.size} bytes
🕒 Created: ${new Date(data.lastModified).toLocaleString()}

📝 Content Preview:
${fileContent.substring(0, 100)}${fileContent.length > 100 ? '...' : ''}

🎯 Pattern Behavior:
- File là Leaf component
- Không thể chứa component khác
- Size = content.length()`;
        
        showResult('composite-result', output, true);
    } else {
        showResult('composite-result', `❌ Error: ${result.error || 'Failed to add file'}`, false);
    }
}

// Observer Pattern Functions
async function createStock() {
    const symbol = document.getElementById('stockSymbol').value;
    const price = parseFloat(document.getElementById('stockPrice').value);
    
    if (!symbol || isNaN(price)) {
        showResult('observer-result', '❌ Vui lòng nhập mã cổ phiếu và giá hợp lệ!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/observer/stock`, 'POST', {
        symbol: symbol,
        price: price
    });
    
    if (result.success) {
        const data = result.data;
        const output = `✅ Stock Created Successfully!

📈 Stock Symbol: ${data.symbol}
💰 Initial Price: $${data.price}
👥 Observer Count: ${data.observerCount}

📋 REGISTERED OBSERVERS:
${data.observers ? data.observers.map(observer => `• ${observer}`).join('\n') : '• API Investor'}

📢 REGISTRATION LOG:
${data.registrationLog ? data.registrationLog.join('\n') : '✅ Observer registered successfully'}

🎯 Observer Pattern Setup Complete:
• Subject: StockPrice (${data.symbol})
• Observers: ${data.observerCount} investors registered
• Ready: Observers will be notified on price changes

💡 Next: Try updating the price to see Observer Pattern in action!`;
        
        showResult('observer-result', output, true);
    } else {
        showResult('observer-result', `❌ Error: ${result.error || 'Failed to create stock'}`, false);
    }
}

async function updatePrice() {
    const symbol = document.getElementById('stockSymbol').value || 'DEMO';
    const newPrice = parseFloat(document.getElementById('newPrice').value);
    
    if (isNaN(newPrice)) {
        showResult('observer-result', '❌ Vui lòng nhập giá mới hợp lệ!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/observer/update-price`, 'POST', {
        symbol: symbol,
        newPrice: newPrice
    });
    
    if (result.success) {
        const data = result.data;
        const output = `🔔 OBSERVER PATTERN IN ACTION!

📈 Stock: ${data.symbol}
📊 Price Change: $${data.oldPrice} → $${data.newPrice}
📈 Change: ${data.priceChange > 0 ? '+' : ''}$${data.priceChange.toFixed(2)} (${data.priceChangePercent}%)
👥 Observers Notified: ${data.notified ? '✅ YES' : '❌ NO'}

📢 DETAILED NOTIFICATIONS:
${data.notifications ? data.notifications.join('\n') : 'No detailed notifications available'}

🎯 PATTERN EXPLANATION:
${data.patternExplanation ? data.patternExplanation.join('\n') : 'Pattern explanation not available'}

✅ Observer Pattern successfully demonstrated!`;
        
        showResult('observer-result', output, data.notified);
    } else {
        showResult('observer-result', `❌ Error: ${result.error || 'Failed to update price'}`, false);
    }
}

// Adapter Pattern Functions
async function processJSON() {
    const jsonData = document.getElementById('jsonData').value;
    
    if (!jsonData) {
        showResult('adapter-result', '❌ Vui lòng nhập JSON data!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/adapter/process-json`, 'POST', {
        data: jsonData
    });
    
    if (result.success) {
        const data = result.data;
        const output = `✅ JSON Processing Successful!

📄 Input Format: ${data.inputFormat}
📊 Processing Result: ${data.success ? 'SUCCESS' : 'FAILED'}

📝 Input Data:
${data.inputData}

🔄 Processing Output:
${data.result}

🎯 Pattern Behavior:
- JSONDataProcessor xử lý trực tiếp
- Không cần Adapter
- Validate JSON format trước khi xử lý`;
        
        showResult('adapter-result', output, data.success);
    } else {
        showResult('adapter-result', `❌ Error: ${result.error || 'Failed to process JSON'}`, false);
    }
}

async function processXML() {
    const xmlData = document.getElementById('xmlData').value;
    
    if (!xmlData) {
        showResult('adapter-result', '❌ Vui lòng nhập XML data!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/adapter/process-xml`, 'POST', {
        data: xmlData
    });
    
    if (result.success) {
        const data = result.data;
        const output = `✅ XML Processing via Adapter!

📄 Input Format: ${data.inputFormat}
📊 Processing Result: ${data.success ? 'SUCCESS' : 'FAILED'}

📝 Original XML:
${data.inputData}

🔄 Converted to JSON:
${data.convertedToJSON}

🎯 Final Processing Result:
${data.result}

🔌 Adapter Pattern in Action:
1. Client gửi XML data
2. XMLToJSONAdapter nhận request
3. Adapter convert XML → JSON
4. JSONDataProcessor xử lý JSON
5. Return kết quả cho Client

💡 Client không biết về XML processing!`;
        
        showResult('adapter-result', output, data.success);
    } else {
        showResult('adapter-result', `❌ Error: ${result.error || 'Failed to process XML'}`, false);
    }
}

async function updateTaskStatus() {
    const taskName = document.getElementById('taskName').value;
    const newStatus = document.getElementById('taskStatus').value;
    
    if (!taskName) {
        showResult('observer-result', '❌ Vui lòng nhập tên task!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/observer/update-task`, 'POST', {
        taskName: taskName,
        newStatus: newStatus
    });
    
    if (result.success) {
        const data = result.data;
        const output = `📋 TASK STATUS UPDATED - TEAM NOTIFIED!

📋 Task: ${data.taskName}
📊 Status Change: "${data.oldStatus}" → "${data.newStatus}"
👤 Assignee: ${data.assignee}
👥 Team Members Notified: ${data.notified ? '✅ YES' : '❌ NO'}

📢 DETAILED TEAM NOTIFICATIONS:
${data.notifications ? data.notifications.join('\n') : 'No detailed notifications available'}

🎯 Observer Pattern for Project Management:
• Subject: TaskStatus (${data.taskName})
• Observers: Team Members (Developer, Tester, PM)
• Each role responds differently to status changes
• Automatic notification ensures team coordination

✅ Task Observer Pattern successfully demonstrated!`;
        
        showResult('observer-result', output, data.notified);
    } else {
        showResult('observer-result', `❌ Error: ${result.error || 'Failed to update task status'}`, false);
    }
}

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    console.log('🎯 Design Patterns Web Demo Loaded!');
    
    // Test API connection
    fetch(`${API_BASE}/health`)
        .then(response => response.json())
        .then(data => {
            console.log('✅ API Connection:', data.message);
        })
        .catch(error => {
            console.error('❌ API Connection Failed:', error);
        });
});

// Observer Pattern Functions với Email Notifications
async function createStock() {
    const symbol = document.getElementById('stockSymbol').value;
    const price = parseFloat(document.getElementById('stockPrice').value);
    
    if (!symbol || isNaN(price)) {
        showResult('observer-result', '❌ Vui lòng nhập mã cổ phiếu và giá hợp lệ!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/observer/stock`, 'POST', {
        symbol: symbol,
        price: price
    });
    
    if (result.success) {
        const data = result.data;
        const output = `✅ Stock Created Successfully!

📈 Stock Symbol: ${data.symbol}
💰 Initial Price: $${data.price}
📧 Email Subscribers: ${data.observerCount}

📋 REGISTERED EMAIL SUBSCRIBERS:
${data.subscribers && data.subscribers.length > 0 ? 
    data.subscribers.map(subscriber => `• ${subscriber}`).join('\n') : 
    '⚠️ No email subscribers yet'}

📢 REGISTRATION LOG:
${data.registrationLog ? data.registrationLog.join('\n') : '✅ Stock created successfully'}

🎯 Observer Pattern Setup Complete:
• Subject: StockPrice (${data.symbol})
• Observers: ${data.observerCount} email subscribers
• Ready: Email notifications will be sent on price changes

💡 ${data.observerCount > 0 ? 
    'Next: Update price to see email notifications!' : 
    'Add email subscribers first, then update price to see Observer Pattern!'}`;
        
        showResult('observer-result', output, true);
    } else {
        showResult('observer-result', `❌ Error: ${result.error || 'Failed to create stock'}`, false);
    }
}

async function updatePrice() {
    const symbol = document.getElementById('stockSymbol').value || 'DEMO';
    const newPrice = parseFloat(document.getElementById('newPrice').value);
    
    if (isNaN(newPrice)) {
        showResult('observer-result', '❌ Vui lòng nhập giá mới hợp lệ!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/observer/update-price`, 'POST', {
        symbol: symbol,
        newPrice: newPrice
    });
    
    if (result.success) {
        const data = result.data;
        const output = `🔔 STOCK PRICE UPDATED - EMAIL NOTIFICATIONS SENT!

📈 Stock: ${data.symbol}
📊 Price Change: $${data.oldPrice} → $${data.newPrice}
📈 Change: ${data.priceChange > 0 ? '+' : ''}$${data.priceChange.toFixed(2)} (${data.priceChangePercent}%)
📧 Emails Sent: ${data.emailsSent}
✅ Notifications: ${data.notified ? 'SUCCESS' : 'NO SUBSCRIBERS'}

${data.emailNotifications ? data.emailNotifications.join('\n') : 'No email notifications available'}

🎯 PATTERN EXPLANATION:
${data.patternExplanation ? data.patternExplanation.join('\n') : 'Pattern explanation not available'}

${data.notified ? 
    '✅ Observer Pattern with Email Notifications successfully demonstrated!' :
    '⚠️ Add email subscribers to see Observer Pattern in action!'}`;
        
        showResult('observer-result', output, data.notified);
    } else {
        showResult('observer-result', `❌ Error: ${result.error || 'Failed to update price'}`, false);
    }
}

async function updateTaskStatus() {
    const taskName = document.getElementById('taskName').value;
    const newStatus = document.getElementById('taskStatus').value;
    
    if (!taskName) {
        showResult('observer-result', '❌ Vui lòng nhập tên task!', false);
        return;
    }
    
    const result = await makeRequest(`${API_BASE}/observer/update-task`, 'POST', {
        taskName: taskName,
        newStatus: newStatus
    });
    
    if (result.success) {
        const data = result.data;
        const output = `📋 TASK STATUS UPDATED - EMAIL NOTIFICATIONS SENT!

📋 Task: ${data.taskName}
📊 Status Change: "${data.oldStatus}" → "${data.newStatus}"
👤 Assignee: ${data.assignee}
📧 Emails Sent: ${data.emailsSent}
✅ Notifications: ${data.notified ? 'SUCCESS' : 'NO SUBSCRIBERS'}

${data.emailNotifications ? data.emailNotifications.join('\n') : 'No email notifications available'}

🎯 Observer Pattern for Project Management:
• Subject: TaskStatus (${data.taskName})
• Observers: Email Subscribers
• Real-world: Team members get email alerts for task changes
• Automatic notification ensures team coordination

${data.notified ? 
    '✅ Task Observer Pattern with Email Notifications successfully demonstrated!' :
    '⚠️ Add email subscribers to see Observer Pattern in action!'}`;
        
        showResult('observer-result', output, data.notified);
    } else {
        showResult('observer-result', `❌ Error: ${result.error || 'Failed to update task status'}`, false);
    }
}