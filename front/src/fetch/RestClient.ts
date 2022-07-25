const HOST = process.env.REACT_APP_BACK_HOST;
const PORT = process.env.REACT_APP_BACK_PORT;
const fixedUrl : string = `http://${HOST}:${PORT}/api/shared`;


export default async function http<T>(url = '', method = 'GET', data = {}): Promise<T> {
  const response = await fetch(fixedUrl + url, {
    method: method, // *GET, POST, PUT, DELETE, etc.
    //mode: 'cors', // no-cors, *cors, same-origin
    headers: method === 'POST' ? {
      'Content-Type': 'application/json'
    } : undefined,
    body: method === 'POST' ? JSON.stringify(data) : undefined // body data type must match "Content-Type" header
  });

  if(method === 'GET'){
    return await response.json();
  }

  return await response.text() as unknown as T; 
}