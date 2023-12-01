import React, { useEffect, useState, createElement } from "react"
import { Navigate, useLocation } from "react-router-dom"
import { useAuth } from "../context/AuthContext"
import { Layout, List } from 'antd';

const { Content } = Layout;

function BuyTicket() {
    const Auth = useAuth()
    const user = Auth.getUser()

    const [wagonsInfo, setWagonsInfo] = useState()
    const [wagonListData, setWagonListData] = useState()
    const [wagon, setWagon] = useState()
    const [wagonLayout, setWagonLayout] = useState()
    const [seat, setSeat] = useState()
    const [departureTooLate, setDepartureTooLate] = useState(false)
    const [clientHaveTicket, setClientHaveTicket] = useState(false)
    const [seatBusy, setSeatBusy] = useState(false)

    const location = useLocation()

    useEffect(() => {
        if(!wagonsInfo && location.state){
            fetch(`${process.env.REACT_APP_URL}/api/v1/clients/wagons?train_number=${location.state.train}&schedule_id=${location.state.key}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + user.token
                },
            })
            .then(response => {
                if(response.ok){
                    response.json()
                    .then(json => {
                        setWagonsInfo(json)
                    })
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")
        }
    })

    useEffect(() => {
        if(wagonsInfo){
            const data = wagonsInfo.map((wagon) => (`Wagon number ${wagon.number} seats available: ${wagon.availableSeats}/${wagon.seatCount}`))

            setWagonListData(data)
        }
    },[wagonsInfo])

    useEffect(() => {
        if(wagon){
            let rows = []
            let seats = []
            for(const seat of wagon.seats){
                let color = seat.available ? "success" : "danger"

                seats.push(createElement(
                    'div',
                    { className: "col-2", key:seat.seatNumber },
                    <button type="button" className={"btn btn-" + color} onClick={() => {setSeat(seat)}}>{seat.seatNumber}</button>
                ))

                if(seat.seatNumber % wagon.seatsPerRow === 0 || seat.seatNumber === wagon.seatCount){
                    rows.push(createElement(
                        'div',
                        { className: "row justify-content-md-center pt-4", key:seat.seatNumber },
                        seats
                    ))
                    seats = []
                }
            }

            let body = createElement(
                'div',
                { className: "container text-center pt-4" },
                <h4 key={2}>Choose the seat:</h4>,
                rows
            )

            setWagonLayout(body)
            setSeatBusy(false)
        }
    },[wagon])

    const noWagonStyle = () => {
        return wagon ? { "display": "block" } : { "display": "none" }
    }

    const noSeatStyle = () => {
        return seat && seat.available ? { "display": "block" } : { "display": "none" }
    }

    const seatNumber = () => {
        if(seat){
            return seat.seatNumber
        }
        
        return null
    }

    const buyTicket = async e => {
        e.preventDefault()

        await fetch(`${process.env.REACT_APP_URL}/api/v1/clients/ticket`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + user.token
            },
            body: JSON.stringify({
                token: user.token,
                seat: {
                    number: seat.seatNumber,
                    wagonNumber: wagon.number,
                    trainNumber: location.state.train
                },
                schedule: {
                    scheduleId: location.state.key
                }
            })
        })
        .then(response => {
            if(response.ok){
                window.location.href = "/profile"
            }
            else {
                response.text()
                .then(text => {
                    if(text === "too late"){
                        setDepartureTooLate(true)
                    }
                    else if(text === "already have ticket"){
                        setClientHaveTicket(true)
                    }
                    else if(text === "seat not free"){
                        setSeatBusy(true)
                        setWagonsInfo(null)
                        setWagon(null)
                        setSeat(null)
                    }
                    else{
                        Auth.userLogout()
                        window.location.href = "/login"
                    }
                })
            }
        })
        .catch(() => window.location.href = "/error")
    }

    const noErrorStyle = () => {
        return departureTooLate || clientHaveTicket || seatBusy ? { "display": "block" } : { "display": "none" }
    }

    if(!Auth.userIsAuthenticated() || !location.state){
        return <Navigate to={'/'} />
    }

    return (
        <div>
            <div style={{position:"absolute", width: "100%", height: "94vh", minHeight:"900px", backgroundImage: "url(/home_bg.jpg)", backgroundRepeat: "no-repeat", backgroundSize: "cover", backgroundAttachment: "fixed", filter: "blur(6px)"}}></div>
            <div style={{filter: "blur(0px)"}}>
                <div className="container text-center py-5" style={noErrorStyle()}>
                    <div className="row justify-content-md-center">
                        <div className="col-sm-auto">
                            {departureTooLate && <h5>The train has already departure or is about to.</h5>}
                            {clientHaveTicket && <h5>The client already has a ticket for this schedule.</h5>}
                            {seatBusy && <h5>The seat is not available.</h5>}
                        </div>
                    </div>
                </div>
                <Layout hasSider style={{backgroundColor: "#FFFFFF", filter: "blur(0px)"}}>
                    <Content>
                        <div className="p-5">
                            <h3>Schedule information:</h3>
                            <ul className="pt-3 fs-6">
                                <li>Train: {location.state.train}</li>
                                <li>Start Station: {location.state.startStation}</li>
                                <li>End Station: {location.state.endStation}</li>
                                <li>Departure: {location.state.departure}</li>
                                <li>Arrival: {location.state.arrival}</li>
                            </ul>
                            <h3>Wagons information:</h3>
                            <List dataSource={wagonListData} renderItem={(item) => 
                                <List.Item actions={[<button className="btn" style={{backgroundColor: "#c7d2f0"}} onClick={() => { setWagon(wagonsInfo[item.split(" ")[2]-1]) }}>Select</button>]}>
                                    {item}
                                </List.Item>} 
                            />
                        </div>
                    </Content>
                    <Content>
                        <div style={noWagonStyle()}>
                            {wagonLayout}
                            <div className="container text-center pt-5">
                                <div className="row justify-content-md-center">
                                    <div className="col-sm-auto">
                                        <h5>Available</h5>
                                    </div>
                                    <div className="col-1">
                                        <button className="btn btn-success"></button>
                                    </div>
                                </div>
                                <div className="row justify-content-md-center">
                                    <div className="col-sm-auto">
                                        <h5>Not available</h5>
                                    </div>
                                    <div className="col-1">
                                        <button className="btn btn-danger"></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Content>
                    <Content>
                        <div className="pt-5" style={noSeatStyle()}>
                            <h3>Buy ticket for seat number {seatNumber()}</h3>
                            <div className="container">
                                <div className="row d-flex justify-content-left py-4">
                                    <div className="col-5">
                                        <form onSubmit={buyTicket}>
                                            <div className="d-flex justify-content-between align-items-center mb-3">
                                                <div className="form-outline">
                                                <input type="text" className="form-control form-control-lg" placeholder="1234 5678 9012 3457"/>
                                                <label className="form-label">Card Number</label>
                                                </div>
                                            </div>
                                            <div className="d-flex justify-content-between align-items-center mb-4">
                                                <div className="form-outline">
                                                <input type="text" className="form-control form-control-lg" placeholder="Cardholder's Name" />
                                                <label className="form-label">Cardholder's Name</label>
                                                </div>
                                            </div>
                                            <div className="d-flex justify-content-between align-items-center pb-2">
                                                <div className="form-outline">
                                                <input type="text"className="form-control form-control-lg" placeholder="MM/YYYY"/>
                                                <label className="form-label">Expiration</label>
                                                </div>
                                                <div className="form-outline">
                                                <input type="password" id="typeText2" className="form-control form-control-lg" placeholder="&#9679;&#9679;&#9679;"/>
                                                <label className="form-label">Cvv</label>
                                                </div>
                                            </div>
                                            <button className="btn btn-lg" style={{backgroundColor: "#c7d2f0"}}>Buy</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Content>
                </Layout>
            </div>
        </div>
    )
}

export default BuyTicket