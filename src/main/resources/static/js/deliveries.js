async function fetchUrl(url){
    const response = await fetch(url)
    return await response.json()
}


async function scheduleDelivery(delivery){
    console.log("add drone to delivery #"+delivery.id)
    const url = "http://localhost:8080/api/v1/deliveries/"+delivery.id+"/schedule"
    const scheduledDelivery = await fetchUrl(url)
    console.log(scheduledDelivery)
}

async function fetchDeliveries(){
    const url = "http://localhost:8080/api/v1/deliveries"
    console.log("fetching deliveries")
    const data = await fetchUrl(url)

    const tableBody = document.getElementById('tableBody')
    tableBody.innerHTML = ""

    data.forEach(delivery => {

        const row = document.createElement("tr")

        let deliveryDroneStatus = "Unassigned";
        if (delivery.drone !== undefined){
            deliveryDroneStatus = "Assigned"
        }

        let deliveryStatus = delivery.actualDeliveryTime
        if(delivery.actualDeliveryTime == null){
            if(delivery.drone !== undefined){
                deliveryStatus = "Being delivered"
            }else {
                deliveryStatus = "Waiting for Drone"
            }
        }

        row.innerHTML =
            "<td>" + delivery.id + "</td>" +
            "<td>" + delivery.address + "</td>" +
            "<td>" + deliveryDroneStatus + "</td>" +
            "<td>" + delivery.expectedDeliveryTime + "</td>" +
            "<td>" + deliveryStatus + "</td>" +
            "<td> <button class='scheduleBtn' id='scheduleBtn" + delivery.id +
            "' value='" + delivery + "'>Schedule Drone</button> </td>"

        tableBody.appendChild(row)

        const scheduleBtn = document.getElementById("scheduleBtn" + delivery.id)

        scheduleBtn.addEventListener("click", () => {
            scheduleDelivery(delivery)
        })
    })
}

async function orderPizza(){
    const orderForm = document.getElementById("orderForm")
    orderForm.addEventListener("submit", async (e) => {
        e.preventDefault()
        const pizzaType = document.getElementById("pizzaType").value
        const address = document.getElementById("address").value

        const response = await fetch("http://localhost:8080/api/v1/deliveries/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({address, pizzaType})
        })
        console.log("Response :" + await response.json())
    })
}

//automatically updates the list by fetching at set intervals
window.addEventListener("load", function (){
    const fetchInterval = 8000 //8 second interval
    setInterval(fetchDeliveries,fetchInterval)
})

fetchDeliveries()
orderPizza()
