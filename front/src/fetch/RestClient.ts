import React from 'react';

const fixedUrl : string = "http://localhost:8080/api/shared";


export default async function http<T>(url = '', method = 'GET', data = {}): Promise<T> {
  console.log('Url fetching is ' + fixedUrl + url);
  const response = await fetch(fixedUrl + url, {
    method: method, // *GET, POST, PUT, DELETE, etc.
    //mode: 'cors', // no-cors, *cors, same-origin
    headers: method === 'POST' ? {
      'Content-Type': 'application/json'
    } : undefined,
    body: method !== 'GET' ? JSON.stringify(data) : undefined // body data type must match "Content-Type" header
  });

  const reponseJson = await response.json(); // parses JSON response into native JavaScript objects

  return reponseJson; 
}