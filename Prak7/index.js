"use strict";
let version = 1;
let store;

if (!window.indexedDB) 
    alert("IndexedDB is not available!");

function init() {
    var req = window.indexedDB.deleteDatabase("helpdesk");
    req.onsuccess = (arg) => { console.log("Helpdesk database deleted successfully!"); };
    req.onerror = (arg) => { console.error("Error deleting Helpdesk database!"); };

    var req = window.indexedDB.open("helpdesk", version);
    req.onsuccess = (event) => { console.log("Database created!"); };
    req.onerror = function (event) { console.error("Error creating database!"); };

    req.onupgradeneeded = function (event) {
        var db = this.result;

        if (!db.objectStoreNames.contains('Clients')) {
            store = db.createObjectStore('Clients', {
                keyPath: 'ID',
                autoIncrement: true
            });
            console.log("ObjectStore 'Clients' created!");
        }

        if (!db.objectStoreNames.contains('Consultants')) {
            store = db.createObjectStore('Consultants', {
                keyPath: 'ID',
                autoIncrement: true
            });
            console.log("ObjectStore 'Consultants' created!");
        }

        if (!db.objectStoreNames.contains('Calls')) {
            store = db.createObjectStore('Calls', {
                keyPath: 'ID',
                autoIncrement: true
            });
            
            console.log("ObjectStore 'Calls' created!");
        }
    };
}

function fill(count){
    var req = window.indexedDB.open("helpdesk", version);
    req.onerror = (event) => { console.log ("Error opening database!"); };

    req.onsuccess = function(event) {
        var db = this.result;
        // Create clients
        var trans = db.transaction(['Clients'], 'readwrite');
        var store = trans.objectStore('Clients');
        store.put({ ID: 1, Name: 'Neil', Phone: 12345678, Account: 100 });
        store.put({ ID: 2, Name: 'Stu', Phone: 87654321, Account: 200 });
        store.put({ ID: 3, Name: 'Murdoc', Phone: 18273645, Account: 300 });

        // Create consultants
        var trans = db.transaction(['Consultants'], 'readwrite');
        var store = trans.objectStore('Consultants');
        store.put({ ID: 1, Name: 'Daveed', Phone: 45362718, Mobile: 1});
        store.put({ ID: 2, Name: 'Isaac', Phone: 12348765, Mobile: 1});
        store.put({ ID: 3, Name: 'Lisbeth', Phone: 81726354, Mobile: 1});
    
        //Create calls
        var trans = db.transaction(['Calls'], 'readwrite');
        var store = trans.objectStore('Calls');
        var i = 0;
        while (i < count) {
            store.put({
                ID: ++i,
                Day: Math.floor(Math.random() * 30),
                Time: Math.floor(Math.random() * 1000),
                Duration: Math.floor(Math.random() * 90) + 10,
                Client: Math.floor(Math.random() * 3),
                Consultant: Math.floor(Math.random() * 3) });
            var li = document.createElement("li");
            li.appendChild(document.createTextNode(i));
            li.style = "cursor: pointer;";
            li.addEventListener('click', function(){printElement(parseInt(this.textContent))}, true);
            document.getElementById('list').appendChild(li);
            document.getElementById('list').style.visibility = "visible";
        }
        
        console.log("Database filled successfully!");
        db.close();
    };
}

function printElement(id) {
    var req = window.indexedDB.open("helpdesk", version);
    
    req.onsuccess = function(event) {
        console.log(id)
        var db = this.result;
        var trans = db.transaction(['Calls'], 'readonly');
        var store = trans.objectStore('Calls');
        var get = store.get(id);
        get.onsuccess = function(arg){
            document.getElementById("elementDisplay").innerText = JSON.stringify(get.result, null, 1);
            console.log(get.result);
        }
        db.close();
    }
}

function show() {
    var req = window.indexedDB.open("helpdesk", version);
    req.onerror = (event) => { console.error("Error opening database!"); };
    
    req.onsuccess = function(event) {
        var db = this.result;
        var trans = db.transaction(['Calls'], 'readonly');
        var store = trans.objectStore('Calls');
        var cursor = store.openCursor(IDBKeyRange.lowerBound(0));
        cursor.onsuccess = function(arg){
            var result = arg.target.result;
            if(result){
                console.log(result);
                result.continue();
            }
        };
        db.close();
    }
}

function meanDuration() {
    var req = window.indexedDB.open("helpdesk", version);
    req.onerror = (event) => { console.error("Error opening database!") };

    req.onsuccess = function(event) {
        var count = 0, total = 0;
        var db = this.result;
        var trans = db.transaction(['Calls'], 'readwrite');
        var store = trans.objectStore('Calls');
        var cursor = store.openCursor(IDBKeyRange.lowerBound(0));
        cursor.onsuccess = (args) => {
            var result = args.target.result;
            if(result){
                total += result.value.Duration;
                count++;
                result.continue();
            }else { document.getElementById("meanDuration").innerText = "Average Call Duration: " + total / count; }
        };
        db.close();
    }
}