function csrf() {
    let req = new XMLHttpRequest();
    req.open('GET', document.location, false);
    req.send(null);
    let headers = req.getAllResponseHeaders().split("\n")
        .map(x => x.replace("\r", ""))
        .map(x => x.split(/: */, 2))
        .filter(x => x[0])
        .reduce((ac, x) => { ac[x[0]] = x[1]; return ac; }, {});
    return headers["x-csrf-token"];
}