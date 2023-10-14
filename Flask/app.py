from flask import Flask, request, jsonify
from link_scan_RF import Link_Scan_RF
from link_scan_DT import Link_Scan_DT
from link_scan_SVM import Link_Scan_SVM
from link_scan_GBA import Link_Scan_GBA
from link_scan_GBM import Link_Scan_GBM
from link_scan_GBM_Song import Link_Scan_GBM_Song

app = Flask(__name__)

@app.route('/link', methods=['POST'])
def link_check():
    data = request.get_data()  # 문자열 데이터를 가져옴
    link = data.decode('utf-8')  # 문자열을 UTF-8로 디코딩
    result=0

    if link is not None:
        print(f"Link: {link}")
        result=Link_Scan_GBM_Song(link)
        print(result)
        # 응답 데이터 구성
        response_data = f'Received link: {result}'
        return response_data
    else:
        # 유효한 데이터가 오지 않았을 때의 처리
        response_data = 'Invalid data received'
        return response_data

if __name__ == '__main__':
    