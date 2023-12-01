import React, { useState, useEffect, useRef } from "react"
import { Navigate } from "react-router-dom"
import { useAuth } from "../context/AuthContext"
import { Select, Table, Tabs, Layout, Button, Input, Space } from 'antd'
import { SearchOutlined } from '@ant-design/icons'

const { Content } = Layout;

function EmployeePage() {
    const Auth = useAuth()
    const user = Auth.getUser()

    const [currentTab, setCurrentTab] = useState(1)

    const [trainNumber, setTrainNumber] = useState()
    const [wagonCount, setWagonCount] = useState()
    const [seatCount, setSeatCount] = useState()
    const [seatPerRow, SetSeatPerRow] = useState()
    const [trainDataError, setTrainDataError] = useState(false)
    const [trainNumberError, setTrainNumberError] = useState(false)

    const [stationNames, setStationNames] = useState()
    const [startSelectValues, setStartSelectValues] = useState()
    const [endSelectValues, setEndSelectValues] = useState()

    const [startStation, setStartStation] = useState()
    const [endStation, setEndStation] = useState()
    const [scheduleTrainNumber, setScheduleTrainNumber] = useState()
    const [departure, setDeparture] = useState()
    const [arrival, setArrival] = useState()
    const [timeIntervalError, setTimeIntervalError] = useState()
    const [scheduleTrainError, setScheduleTrainError] = useState()

    const [stationName, setStationName] = useState()
    const [stationNameExists, setStationNameExists] = useState(false)

    const [trainData, setTrainData] = useState()
    const [scheduleData, setScheduleData] = useState()
    const [stationData, setStationData] = useState()
    const [passengersData, setPassengersData] = useState()

    const [scheduleId, setScheduleId] = useState()

    useEffect(() => {
        if(!stationNames && user){
            fetch(`${process.env.REACT_APP_URL}/api/v1/employee/station_names`, {
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
                        setStationNames(json)
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")
        }

        if(!trainData && user){
            fetch(`${process.env.REACT_APP_URL}/api/v1/employee/trains`, {
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
                        let values = json.map((train) => ({key: train.trainId, id: train.trainId, number: train.number, wagons: train.wagonCount, seats: train.seatCount}))
                        setTrainData(values)
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")
        }

        if(!scheduleData && user){
            fetch(`${process.env.REACT_APP_URL}/api/v1/employee/schedules`, {
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
                        let values = json.map((schedule) => ({
                            key: schedule.scheduleId,
                            id: schedule.scheduleId,
                            train: schedule.train.number,
                            startStation: schedule.startStation.name,
                            endStation: schedule.endStation.name,
                            departure: schedule.departureTime.replace("T", " "),
                            arrival: schedule.arrivalTime.replace("T", " ")
                        }))
                        setScheduleData(values)
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")
        }

        if(!stationData && user){
            fetch(`${process.env.REACT_APP_URL}/api/v1/employee/stations`, {
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
                        let values = json.map((station) => ({key: station.stationId, id: station.stationId, name: station.name}))
                        setStationData(values)
                    });
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
        if(stationNames){
            let values = []
            for(const station of stationNames){
                if(station !== startStation){
                    values.push({
                        value: station,
                        label: station,
                    })
                }
            }
            setEndSelectValues(values)
        }
    },[startStation, stationNames]);

    useEffect(() => {
        if(stationNames){
            let values = []
            for(const station of stationNames){
                if(station !== endStation){
                    values.push({
                        value: station,
                        label: station,
                    })
                }
            }
            setStartSelectValues(values)
        }
    },[endStation, stationNames]);

    useEffect(() => {
        if(scheduleId && user){
            fetch(`${process.env.REACT_APP_URL}/api/v1/employee/passengers?schedule_id=${scheduleId}`, {
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
                        let values = json.map((passenger, index) => ({
                            key: index, 
                            username: passenger.username, 
                            name: passenger.name, 
                            surname: passenger.surname, 
                            birthDate: passenger.birthDate
                        }))
                        setPassengersData(values)
                    });
                }
                else{
                    Auth.userLogout()
                    window.location.href = "/login"
                }
            })
            .catch(() => window.location.href = "/error")
        }
    },[scheduleId, Auth, user]);

    const createTrain = async e => {
        e.preventDefault()

        if(wagonCount > 0 && trainNumber > 0 && seatCount > 0 && seatPerRow > 0){
            let wagons = []
            for(let i=0; i<wagonCount; i++){
                wagons.push({
                    seatCount: seatCount,
                    seatPerRow: seatPerRow
                })
            }

            await fetch(`${process.env.REACT_APP_URL}/api/v1/employee/trains`, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + user.token
                },
                body: JSON.stringify({
                    train: {
                        number: trainNumber
                    },
                    wagons: wagons
                })
            })
            .then(response => {
                if(response.ok){
                    setCurrentTab(2)
                    setTrainDataError(false)
                    setTrainNumberError(false)
                    setTrainData(null)
                }
                else {
                    response.text()
                    .then(text => {
                        if(text === "train number exists"){
                            setTrainNumberError(true)
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
        else {
            setTrainDataError(true)
        }
    }

    const createSchedule = async e => {
        e.preventDefault()

        if(startStation && endStation)
        {
            if(new Date() < Date.parse(departure) && Date.parse(departure) < Date.parse(arrival))
            {
                await fetch(`${process.env.REACT_APP_URL}/api/v1/employee/schedules`, {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + user.token
                    },
                    body: JSON.stringify({
                        departureTime: departure,
                        arrivalTime: arrival,
                        train: {
                            number: scheduleTrainNumber
                        },
                        startStation: {
                            name: startStation
                        },
                        endStation: {
                            name: endStation
                        }
                    })
                })
                .then(response => {
                    if(response.ok){
                        setCurrentTab(2)
                        setTimeIntervalError(false)
                        setScheduleTrainError(false)
                        setScheduleData(null)
                    }
                    else {
                        response.text()
                        .then(text => {
                            if(text.includes("Train not found")){
                                setScheduleTrainError(true)
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
            else {
                setTimeIntervalError(true)
            }
        }
    }

    const createStation = async e => {
        e.preventDefault()

        await fetch(`${process.env.REACT_APP_URL}/api/v1/employee/stations`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + user.token
            },
            body: JSON.stringify({
                name: stationName,
            })
        })
        .then(response => {
            if(response.ok){
                setCurrentTab(2)
                setStationNameExists(false)
                setStationData(null)
            }
            else {
                response.text()
                .then(text => {
                    if(text === "station name exists"){
                        setStationNameExists(true)
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
    
    const filterOption = (input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase());

    const noErrorStyle = () => {
        return stationNameExists || trainDataError || trainNumberError || timeIntervalError || scheduleTrainError ? { "display": "block" } : { "display": "none" }
    }

    const searchInput = useRef(null);
    const getColumnSearchProps = (dataIndex) => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters, close }) => (
        <div
            style={{
            padding: 8,
            }}
            onKeyDown={(e) => e.stopPropagation()}
        >
            <Input
            ref={searchInput}
            placeholder={`Search ${dataIndex}`}
            value={selectedKeys[0]}
            onChange={(e) => setSelectedKeys(e.target.value ? [e.target.value] : [])}
            onPressEnter={() => confirm()}
            style={{
                marginBottom: 8,
                display: 'block',
            }}
            />
            <Space>
            <Button
                type="primary"
                onClick={() => confirm()}
                icon={<SearchOutlined />}
                size="small"
                style={{
                width: 90,
                }}
            >
                Search
            </Button>
            <Button
                onClick={() => clearFilters && clearFilters()}
                size="small"
                style={{
                width: 90,
                }}
            >
                Reset
            </Button>
            </Space>
        </div>
        ),
        filterIcon: (filtered) => (
        <SearchOutlined
            style={{
            color: filtered ? '#1677ff' : undefined,
            }}
        />
        ),
        onFilter: (value, record) =>
        record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownOpenChange: (visible) => {
        if (visible) {
            setTimeout(() => searchInput.current?.select(), 100);
        }
        },
    });

    const trainColumns = [
        {
            title: 'Id',
            dataIndex: 'id',
            key: 'id',
            sorter: (a, b) => a.id - b.id,
            sortDirections: ['ascend', 'descend', 'ascend'],
        },
        {
            title: 'Number',
            dataIndex: 'number',
            key: 'number',
            sorter: (a, b) => a.number - b.number,
            sortDirections: ['ascend', 'descend', 'ascend'],
            ...getColumnSearchProps('number'),
        },
        {
            title: 'Wagons',
            dataIndex: 'wagons',
            key: 'wagons',
        },
        {
            title: 'Seats per wagon',
            dataIndex: 'seats',
            key: 'seats',
        },
    ]

    const scheduleColumns = [
        {
            title: 'Id',
            dataIndex: 'id',
            key: 'id',
            sorter: (a, b) => a.id - b.id,
            sortDirections: ['ascend', 'descend', 'ascend'],
        },
        {
            title: 'Train',
            dataIndex: 'train',
            key: 'train',
            ...getColumnSearchProps('train'),
        },
        {
            title: 'Start station',
            dataIndex: 'startStation',
            key: 'startStation',
            ...getColumnSearchProps('startStation'),
        },
        {
            title: 'End station',
            dataIndex: 'endStation',
            key: 'endStation',
            ...getColumnSearchProps('endStation'),
        },
        {
            title: 'Departure',
            dataIndex: 'departure',
            key: 'departure',
            sorter: (a, b) => Date.parse(a.departure) - Date.parse(b.departure),
            sortDirections: ['ascend', 'descend', 'ascend'],
        },
        {
            title: 'Arrival',
            dataIndex: 'arrival',
            key: 'arrival',
            sorter: (a, b) => Date.parse(a.arrival) - Date.parse(b.arrival),
            sortDirections: ['ascend', 'descend', 'ascend'],
        },
        {
            title: '',
            key: 'action',
            render: (_, record) => (
                <button type="button" className="btn btn-link btn-sm" onClick={()=>{setScheduleId(record.id); setCurrentTab(3)}}>Passengers</button>
            ),
        }
    ]

    const stationColumns = [
        {
            title: 'Id',
            dataIndex: 'id',
            key: 'id',
            sorter: (a, b) => a.id - b.id,
            sortDirections: ['ascend', 'descend', 'ascend'],
        },
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            ...getColumnSearchProps('name'),
        }
    ]

    const passengersColumns = [
        {
            title: 'Username',
            dataIndex: 'username',
            key: 'username',
            sorter: (a, b) => a.username.localeCompare(b.username),
            sortDirections: ['ascend', 'descend', 'ascend'],
            ...getColumnSearchProps('username'),
        },
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            sorter: (a, b) => a.name.localeCompare(b.name),
            sortDirections: ['ascend', 'descend', 'ascend'],
            ...getColumnSearchProps('name'),
        },
        {
            title: 'Surname',
            dataIndex: 'surname',
            key: 'surname',
            sorter: (a, b) => a.surname.localeCompare(b.surname),
            sortDirections: ['ascend', 'descend', 'ascend'],
            ...getColumnSearchProps('surname'),
        },
        {
            title: 'Birth Date',
            dataIndex: 'birthDate',
            key: 'birthDate',
            sorter: (a, b) => Date.parse(a.birthDate) - Date.parse(b.birthDate),
            sortDirections: ['ascend', 'descend', 'ascend'],
        }
    ]

    if(!(user && user.role === 'E')){
        return <Navigate to="/"/>
    }

    return (
        <div>
            <div style={{position:"absolute", width: "100%", height: "94vh", minHeight:"1050px", minWidth:"1450px", backgroundImage: "url(/home_bg.jpg)", backgroundRepeat: "no-repeat", backgroundSize: "cover", backgroundAttachment: "fixed", filter: "blur(6px)"}}></div>
            <Tabs
                style={{minWidth: "1450px"}}
                activeKey={currentTab}
                onTabClick={key => setCurrentTab(key)}
                centered
                tabBarStyle={{backgroundColor:"#FFFFFF"}}
                size="large"
                items={[{
                    label: 'Create',
                    key: 1,
                    children:
                    <div>
                        <Layout hasSider style={{backgroundColor:"rgba(0,0,0,0)"}}>
                            <Content>
                                <div className="container">
                                    <div className="row row-cols-auto justify-content-md-center">
                                        <div className="p-5 border rounded-3" style={{backgroundColor: "#FFFFFF"}}>
                                            <h3>New Train</h3>
                                            <form onSubmit={createTrain}>
                                                <div className="mb-3 pt-2">
                                                    <label className="form-label">Train number:</label>
                                                    <input type="number" className="form-control form-control-lg" onChange={e => setTrainNumber(e.target.value)} required/>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="form-label">Number of wagons:</label>
                                                    <input type="number" className="form-control form-control-lg" onChange={e => setWagonCount(e.target.value)} required/>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="form-label">Seats per wagon:</label>
                                                    <input type="number" className="form-control form-control-lg" onChange={e => setSeatCount(e.target.value)} required/>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="form-label">Seats per row on wagon:</label>
                                                    <input type="number" className="form-control form-control-lg" onChange={e => SetSeatPerRow(e.target.value)} required/>
                                                </div>
                                                <div className="d-grid">
                                                    <button type="submit" className="btn btn-lg" style={{backgroundColor: "#c7d2f0"}}>Create Train</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </Content>
                            <Content>
                                <div className="container">
                                    <div className="row row-cols-auto justify-content-md-center">
                                        <div className="p-5 border rounded-3" style={{backgroundColor: "#FFFFFF"}}>
                                            <h3>New Schedule</h3>
                                            <form onSubmit={createSchedule}>
                                                <div className="mb-3 pt-2">
                                                    <label className="form-label">Train number:</label>
                                                    <input type="number" className="form-control form-control-lg" onChange={e => setScheduleTrainNumber(e.target.value)} required/>
                                                </div>
                                                <div className="mb-3">
                                                    <div className="pb-2">Start Station:</div>
                                                    <Select
                                                        showSearch
                                                        placeholder="Station name"
                                                        optionFilterProp="children"
                                                        onChange={value => setStartStation(value)}
                                                        filterOption={filterOption}
                                                        size="large"
                                                        options={startSelectValues}
                                                    />
                                                </div>
                                                <div className="mb-3">
                                                    <div className="pb-2">End Station:</div>
                                                    <Select
                                                        showSearch
                                                        placeholder="Station name"
                                                        optionFilterProp="children"
                                                        onChange={value => setEndStation(value)}
                                                        filterOption={filterOption}
                                                        size="large"
                                                        options={endSelectValues}
                                                    />
                                                </div>
                                                <div className="mb-3">
                                                    <div className="pb-2">Departure Time:</div>
                                                    <input type="datetime-local" onChange={e => setDeparture(e.target.value)} required/>
                                                </div>
                                                <div className="mb-3">
                                                    <div className="pb-2">Arrival Time:</div>
                                                    <input type="datetime-local" onChange={e => setArrival(e.target.value)} required/>
                                                </div>
                                                <div className="d-grid">
                                                    <button type="submit" className="btn btn-lg" style={{backgroundColor: "#c7d2f0"}}>Create Schedule</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </Content>
                            <Content>
                                <div className="container">
                                    <div className="row row-cols-auto justify-content-md-center">
                                        <div className="p-5 border rounded-3" style={{backgroundColor: "#FFFFFF"}}>
                                            <h3>New Station</h3>
                                            <form onSubmit={createStation}>
                                                <div className="mb-3 pt-2">
                                                    <label className="form-label">Name:</label>
                                                    <input type="text" className="form-control form-control-lg" onChange={e => setStationName(e.target.value)} required/>
                                                </div>
                                                <div className="d-grid">
                                                    <button type="submit" className="btn btn-lg" style={{backgroundColor: "#c7d2f0"}}>Create Station</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </Content>
                        </Layout>
                        <div className="container text-center py-5" style={noErrorStyle()}>
                            <div className="row justify-content-md-center">
                                <div className="col-sm-auto">
                                    {stationNameExists && <h5>The station name already exists.</h5>}
                                    {trainDataError && <h5>The data of the train is wrong.</h5>}
                                    {trainNumberError && <h5>The train number already exists.</h5>}
                                    {timeIntervalError && <h5>The departure and arrival of the schedule are wrong.</h5>}
                                    {scheduleTrainError && <h5>The train number does not exists.</h5>}
                                </div>
                            </div>
                        </div>
                    </div>
                }, 
                {
                    label: 'View',
                    key: 2,
                    children:
                    <Layout hasSider style={{backgroundColor:"rgba(0,0,0,0)"}}>
                        <Content style={{width: "30%"}}>
                            <div className="container p-5">
                                <h3>Trains</h3>
                                <Table bordered columns={trainColumns} dataSource={trainData} />
                            </div>
                        </Content>
                        <Content style={{width: "55%"}}>
                            <div className="container p-5">
                                <h3>Schedules</h3>
                                <Table bordered columns={scheduleColumns} dataSource={scheduleData} />
                            </div>
                        </Content>
                        <Content style={{width: "20%"}}>
                            <div className="container p-5">
                                <h3>Stations</h3>
                                <Table bordered columns={stationColumns} dataSource={stationData} />
                            </div>
                        </Content>
                    </Layout>
                },
                {
                    label: '',
                    key: 3,
                    children:
                    <div className="container text-center">
                        <div className="row justify-content-md-center">
                            <div className="col-sm-auto">
                                <h3>Passengers from schedule {scheduleId}</h3>
                            </div>
                        </div>
                        <div className="row justify-content-md-center pt-5">
                            <div className="col-sm-auto">
                                <Table bordered columns={passengersColumns} dataSource={passengersData} />
                            </div>
                        </div>
                    </div>
                }]}
            />
        </div>
    )
}

export default EmployeePage