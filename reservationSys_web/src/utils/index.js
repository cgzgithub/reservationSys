//生成UUID
export function createUUID() {
    let d = new Date().getTime();
    const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
      const r = (d + Math.random() * 16) % 16 | 0;
      d = Math.floor(d / 16);
      return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16);
    });
  
    return uuid;
  }

  export function formateMoney(n){
    if(!n) return n;
    let str = n.split('.');
    let re = /\d{1,3}(?=(\d{3})+$)/g;
    let n1 = str[0].replace(re, "$&,");
    return str.length > 1 && str[1] ? `${n1}.${str[1]}` : `${n1}.00`;
  }