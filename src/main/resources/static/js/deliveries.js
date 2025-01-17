async function fetchUrl(url){
    const response = await fetch(url)
    return await response.json()
}


async function fetchDrones(){
    const url = "http://localhost:8080/api/v1/drones"
    console.log("fetching drones")
    const data = await fetchUrl(url)

    const tableBody = document.getElementById('droneTableBody')
    tableBody.innerHTML = ""

    data.forEach(drone => {

        const row = document.createElement("tr")

        row.innerHTML =
            "<td>" + drone.id + "</td>" +
            "<td>" + drone.station.name + "</td>" +
            "<td>" + drone.status + "</td>" +
            "<td>" + drone.uuid + "</td>" +
            "<td> <button class='onOffSwitch' id='onOffSwitch" + drone.id +
            "' value='" + drone + "'>Enable/Disable</button> </td>" +
            "<td> <button class='retireBtn' id='retireBtn" + drone.id +
            "' value='" + drone + "'>Retire</button> </td>"

        tableBody.appendChild(row)

        const onOffSwitch = document.getElementById("onOffSwitch" + drone.id)
        const retireBtn = document.getElementById("retireBtn" + drone.id)

        onOffSwitch.addEventListener("click", async() => {
            console.log("drone status: "+drone.status)
            if(drone.status !== "RETIRED"){
                if(drone.status === "DISABLED"){
                    const url = "http://localhost:8080/api/v1/drones/"+drone.id+"/enable"
                    const enabledDrone = fetchUrl(url)
                    console.log("drone ENABLED :"+enabledDrone)
                }else{
                    const url = "http://localhost:8080/api/v1/drones/"+drone.id+"/disable"
                    const disabledDrone = fetchUrl(url)
                    console.log("drone DISABLED :"+disabledDrone)
                }
            }
        })

        retireBtn.addEventListener("click", async() =>{
            console.log("drone status: "+drone.status)
            if(drone.status === "DISABLED"){ //decided to only allow retiring of disabled drones
                const url = "http://localhost:8080/api/v1/drones/"+drone.id+"/retire"
                const retiredDrone = fetchUrl(url)
                console.log("drone RETIRED :"+retiredDrone)
            }
        })
    })
}


async function fetchDeliveries(){
    const url = "http://localhost:8080/api/v1/deliveries"
    console.log("fetching deliveries")
    const data = await fetchUrl(url)

    const tableBody = document.getElementById('deliveryTableBody')
    tableBody.innerHTML = ""

    data.forEach(delivery => {

        const row = document.createElement("tr")

        let deliveryDroneStatus = "Unassigned";
        if (delivery.drone !== null){
            deliveryDroneStatus = "Assigned"
        }

        let deliveryStatus = delivery.actualDeliveryTime
        if(delivery.actualDeliveryTime == null){
            if(delivery.drone !== null){
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
            "' value='" + delivery + "'>Schedule Drone</button> </td>" +
            "<td> <button class='arrivalBtn' id='arrivalBtn" + delivery.id +
            "' value='" + delivery + "'>Yes</button> </td>"

        tableBody.appendChild(row)

        const scheduleBtn = document.getElementById("scheduleBtn" + delivery.id)
        const arrivalBtn = document.getElementById("arrivalBtn" + delivery.id)

        scheduleBtn.addEventListener("click", () => {
            scheduleDelivery(delivery)
        })

        arrivalBtn.addEventListener("click", () =>{
            deliveryArrived(delivery)
        })
    })
}

async function scheduleDelivery(delivery){
    if(delivery.drone === null) {
        console.log("add drone to delivery #" + delivery.id)
        const url = "http://localhost:8080/api/v1/deliveries/" + delivery.id + "/schedule"
        const scheduledDelivery = await fetchUrl(url)
        console.log("delivery #" + delivery.id + " scheduled: " + scheduledDelivery)
    }else{
        console.log("Delivery is already being delivered")
    }
}

async function deliveryArrived(delivery){
    if(delivery.drone !== null){
        console.log("delivery #"+delivery.id+" arrived at destination")
        const url = "http://localhost:8080/api/v1/deliveries/"+delivery.id+"/finish"
        const finishedDelivery = await fetchUrl(url)
        console.log("delivery #"+delivery.id+" finished at: "+delivery.actualDeliveryTime+
            " --- "+finishedDelivery)
    }else{
        console.log("Delivery cannot be finished: Currently not being delivered")
    }
}

async function addDrone(){
    const addDroneBtn = document.getElementById("addDroneBtn")
    addDroneBtn.addEventListener("click", async() =>{
        const url = "http://localhost:8080/api/v1/drones/add"
        const newDrone = await fetchUrl(url)
        console.log("New drone created: "+newDrone)
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
    setInterval(fetchDrones,fetchInterval)
})

fetchDrones()
fetchDeliveries()
addDrone()
orderPizza()
