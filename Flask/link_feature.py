import requests
import re
import csv
import datetime
import whois
import urllib3
import time
import http.client
import dns.resolver
import socket
import idna


def getLength(url):
    if len(url) < 54:
        length = 0
    else:
        length = 1
    return length

def has_sign(url):
    if '@' in url:
        return 1
    else:
        return 0

def has_sign1(url):
    if '!' in url:
        return 1
    else:
        return 0
    
def has_sign2(url):
    if '=' in url:
        return 1
    else:
        return 0
    
def has_sign3(url):
    if '?' in url:
        return 1
    else:
        return 0
    
def has_sign4(url):
    if '&' in url:
        return 1
    else:
        return 0
    
def has_sign5(url):
    if '+' in url:
        return 1
    else:
        return 0
    
def has_sign6(url):
    if '_' in url:
        return 1
    else:
        return 0
    
def has_sign7(url):
    if '#' in url:
        return 1
    else:
        return 0
    
def has_sign8(url):
    if '-' in url:
        return 1
    else:
        return 0
    
def has_sign9(url):
    url=re.sub('^https?://', '', url)
    if '//' in url:
        return 1
    else:
        return 0

def get_days_since_creation(url):
    url=re.sub('^https?://', '', url)
    if len(url) > 63:
        return 1
    try:
        w = whois.whois(url)
    except whois.parser.PywhoisError:
        return 1
    except requests.exceptions.ConnectionError:
        return 1
    if isinstance(w.creation_date, list):
        creation_date = w.creation_date[0]
    else:
        creation_date = w.creation_date
    if creation_date is None:
        return 1
    else:
        try:
            creation_date = datetime.datetime.strptime(str(creation_date), '%Y-%m-%d %H:%M:%S')
        except ValueError:
            return 1
        days_since_creation = (datetime.datetime.now() - creation_date).days
        if days_since_creation >= 365:
            return 0
        else:
            return 1
        
def rightClick(url):
    try:
        response = requests.get(url, verify=False, timeout=5)
        urllib3.disable_warnings()
    except requests.exceptions.ConnectionError:
        # 컴퓨터와의 연결이 실패한 경우의 예외처리 코드
        return 1
    except requests.exceptions.Timeout as e:
        # Connection timed out
        return 1
    except requests.exceptions.TooManyRedirects as e:
        # Too many redirects
        return 1
    except Exception as e:
        # Other exceptions
        return 1

    if response == "":
        return 1
    else:
        try:
            if re.findall(r"event.button ?== ?2", response.text):
                return 1
            else:
                return 0
        except Exception as e:
            # Other exceptions
            return 1
        
def Iframe_Check(url):
    try:
        response = requests.get(url, verify=False, timeout=5)
        if re.findall(r"<iframe>|<frameBorder>", response.text):
            return 1
        else:
            return 0
    except:
        return 1
        
    
def Dns_Record(url):
    if len(url) > 63:
        return 1
    try:
        # WHOIS 정보 가져오기
        who = whois.whois(url)

        # WHOIS 기록이 없거나 조회할 수 없는 경우
        if who.status is None:
            return 1
        else:
            return 0
    except:
        # WHOIS 조회 중 오류 발생한 경우
        return 1

def Count_Redirects(url):
    try:
        max_redirects = 3
        response = requests.get(url, allow_redirects=False, timeout=5)
        count = 0
        while response.is_redirect and count < max_redirects:
            try:
                count += 1
                response = requests.get(response.headers['Location'], allow_redirects=False, timeout=5)
            except requests.exceptions.RequestException:
                return 1
            except http.client.IncompleteRead:
                return 1
        if count <= 1:
            return 0
        else:
            return 1
    except requests.exceptions.RequestException:
        return 1
    except http.client.IncompleteRead:
        return 1


def Check_link(link):

    url=[]

    urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
    url.append(getLength(link))
    url.append(has_sign(link))
    url.append(has_sign1(link))
    url.append(has_sign2(link))
    url.append(has_sign3(link))
    url.append(has_sign4(link))
    url.append(has_sign5(link))
    url.append(has_sign6(link))
    url.append(has_sign7(link))
    url.append(has_sign8(link))
    url.append(has_sign9(link))
    url.append(get_days_since_creation(link))
    url.append(rightClick(link))
    url.append(Iframe_Check(link))
    url.append(Dns_Record(link))
    url.append(Count_Redirects(link))

    print(url)
    return url
