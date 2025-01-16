async function fetchUrl(url){
    const response = await fetch(url)
    return await response.json()
}


async function scheduleDelivery(delivery){
    console.log("add drone to delivery #"+delivery.id)
    const url = "http://localhost:8080/api/v1/deliveries/"+delivery.id+"/schedule"
    const scheduledDelivery = await fetchUrl(url)
    console.log(scheduledDelivery)
    fetchDeliveries()
}

async function fetchDeliveries(){
    const url = "http://localhost:8080/api/v1/deliveries/queue"
    console.log("fetching deliveries")
    const data = await fetchUrl(url)

    const tableBody = document.getElementById('tableBody')
    tableBody.innerHTML = ""

    data.forEach(delivery => {

        const row = document.createElement("tr")

        row.innerHTML =
            "<td>" + delivery.id + "</td>" +
            "<td>" + delivery.address + "</td>" +
            "<td>" + delivery.expectedDeliveryTime + "</td>" +
            "<td> <button class='scheduleBtn' id='scheduleBtn" + delivery.id +
            "' value='" + delivery + "'>Schedule Drone</button> </td>"

        tableBody.appendChild(row)

        const scheduleBtn = document.getElementById("scheduleBtn" + delivery.id)

        scheduleBtn.addEventListener("click", () => {
            scheduleDelivery(delivery)
        })
    })
}

fetchDeliveries()