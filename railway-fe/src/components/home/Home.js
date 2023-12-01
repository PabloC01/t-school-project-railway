import React from "react"

function HomePage() {
    return (
        <div className="py-5" style={{ width: "100%", height: "94vh", minHeight:"600px", backgroundImage: "url(/home_bg.jpg)", backgroundRepeat: "no-repeat", backgroundSize: "cover", backgroundAttachment: "fixed"}}>
            <div className="p-5 mb-4">
                <div className="container-fluid py-5">
                    <h1 className="display-5 fw-bold">Welcome to Railway</h1>
                    <p className="col-md-8 fs-4 pt-3 pb-3">Experience the magic of travel with our comprehensive railway web page. We're your gateway to a world of adventure, history, and breathtaking landscapes, all accessible by the rhythmic hum of the railway tracks.</p>
                    <div className="row row-cols-auto justify-content-md-left ps-3">
                        <a className="btn btn-primary btn-lg btn-dark" role="button" href="/login">Log in</a>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default HomePage