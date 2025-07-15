from flask import Flask, request, jsonify
from google.oauth2 import service_account
from google.cloud import translate_v2 as translate
from flask_cors import CORS  # ← 추가
import urllib.parse
import requests
app = Flask(__name__)
CORS(app)  # ← CORS 허용 (모든 도메인)

key_data = {
  "type": "service_account",
  "project_id": "fitory-463406",
  "private_key_id": "26c62dff218f979e0dc5163cf73fd8545bde324f",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDlxrZK0Z2rhhOG\niEMqslsSpgN+ZMLlKfp38PJREna/lTdyt9ot1u3EqZOFaqyQcyuVFB1geLZONhZ1\nwH56MYPZUENwBglgcqMyQGhVez936reDaL4N8m7uiA5+XTcd0TcdMONtL91bmPI6\nAvyZh11iuCQd32uhR2Ha1p72tjtti1nwUHedB5uz48dDBX1l8lbACfxsp/3UgMp2\nWQEnwMS9j5KxQ8Hby4E6kcPy0eDASzVLWspFBMf394pFT1LRoOjrpz4m6f9452tj\nhgTDzWOWHxwl+VDb04QTbugpLMMpk29/m0r8USH78t8NWQtSY6aiJjQiyfazVizf\nes/gdlx/AgMBAAECggEAAJSLYk38NXItu3G9pa50n2vu51NO0LBV8xaXLhA7X4l7\nbd07/lCkIhKBjl0yGV1JF2PJHTTA1Rq63MpDJ4DXgeV7fh1tyaIQenCCMvEQPHfJ\nxdmuXS+nCxAp5TTMDlW4a2jzshNkXjZZKj9WrvaSDE1FvhOR3ILnItvW4+2a4dBu\njH6HcTUaqaUX7TjkjEDIkZ6lL8KzhrQ7d4CVKtVOtWMfalmHD+2fLdhZR7XjGDoh\n9N/23sX52VYnRrJcf7/ZSyfzHthNRzpLQmdN3nJu3RP/zi0+7Rb0yleiuUd1qe1O\ns5wX10dllGPqQQv5uH80kOyHJgTIG9H3bDd1f1o5FQKBgQD5l/kd3asDJMYCHa1G\nkds779Cmz7H9lzYIgqAHtpr0GjzJ9hVAIZhf/cpe5FVxqdHL1/QdIFhdweKR11af\nn8pQQKKA0wubRKyh0QhnNfQZH6yftwPBVrnoW43jUMzZgol/MVZF1jiZLUWNNwmr\njLaFKqcsQnGlIgtllX0/Secy2wKBgQDrrIXairKlUmTQQj2AE8Vl+/o20n//nMun\nSwqxpQFmYRIfZWu+Hzt4rzN6ntsXg/KNyiVUmz5molstQOab0iua+xPHsx5k7oK8\nHmFZ4OkNWH8KeCk4YO57i+imntu/SkYQ6tEVlqWDuQeV9OQ6sOiWDWSfZLTuabtS\nOGKNA0UELQKBgQDe8i0HzHVxPWvRgAjDPI4EH9bU6jknN+Z/+45O1YHcAYPDMxkQ\nS/H77IrT8EmSWgqYdrILQlUsin+DnkI2G1lwDgwZ3mpDqzg44qsK5lrABlb/405r\naLhvITjAj/Arajq/bqaNgyjTSmkSFtOAdRSkJtFk64Tv6FUdzEYwPidSeQKBgQCS\nd4Qw0CY6rLxO2fwSzzbNLQ7SZ3x5Rd8jt3WL9fqqwAVdUdDmNc7mMCyUNon/UVe7\nWzhcHqdnbduwgjdP/Axxoz0lzWJEZB8H/vGNxEPSsX8y6H7ltQhB7C/I7c2wUt68\nwNL00c1H0gSu46W7/Bpwtx4kGZiA22E5tiU5ciDAeQKBgQDUIlwo9NnEoO/ULmef\ntPPm4qHcNBnd2acw+9y6SuHhWH4An9XCYkcUZ6+K0Eg+dnNTNLV+zO07J4V2/UJl\n8xABLQcsDW37GfFerwIiOvTYk/vU7G8vc/9xtirpNsqxI9MpBVji7HMlneHdguvz\n+HWEV39BTTo2Ykcr5tDybBLYMA==\n-----END PRIVATE KEY-----\n",
  "client_email": "translation-service-account@fitory-463406.iam.gserviceaccount.com",
  "client_id": "107122354241201136668",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/translation-service-account%40fitory-463406.iam.gserviceaccount.com",
  "universe_domain": "googleapis.com"
}

credentials = service_account.Credentials.from_service_account_info(key_data)
translate_client = translate.Client(credentials=credentials)

@app.route("/translate")
def translate_to_english():
    text = request.args.get('text', '')
    if not text:
        return jsonify({'error': 'Missing text'}), 400

    result = translate_client.translate(text, source_language='ko', target_language='en')
    return jsonify({'translatedText': result['translatedText']})


@app.route('/recommendation', methods=['POST'])
def recommendation():
    data = request.get_json()  # JSON body 파싱
    if not data:
        return jsonify({'error': 'No data received'}), 400
    
    # 받은 데이터 출력 (디버깅용)
    print("Received data:", data)

    # 예: data 안에 currentWeight, targetWeight 등 모두 있음
    current_weight = data.get('currentWeight')
    target_weight = data.get('targetWeight')
    bmr = data.get('bmr')
    rdi = data.get('rdi')
    category = data.get('category')
    sodium = data.get('sodium')
    protein = data.get('protein')
    sugar = data.get('sugar')
    restriction = data.get('restriction')
    recommend_count = data.get('recommendCount')


    result = get_data_by_category(category)

    
    re_list = recommend_meals(result, current_weight, target_weight, rdi, sodium, protein, sugar, restriction, recommend_count)    
    
    for food in re_list:
        print(food)
    # 여기서 로직 처리 후 추천 결과 생성 (예시)
    recommended_foods = [
        {
            'name': food['food_name'],
            'calories': food['calories'],
            'carbohydrate': food['carbohydrate'],
            'protein': food['protein'],
            'fat': food['fat'],
            'sugar': food['sugar'],
            'sodium': food['sodium']
        }
        for food in re_list
    ]

    return jsonify({'recommendations': recommended_foods})

    
    
def parse_serving_size(size_str):
    if not size_str:
        return 100.0
    size_str = size_str.strip()
    for unit in ['g', 'mL']:
        if size_str.endswith(unit):
            try:
                return float(size_str.replace(unit, ''))
            except ValueError:
                return 100.0
    try:
        return float(size_str)
    except ValueError:
        return 100.0

def get_data_by_category(food_cat1_name):

    encoded_food_cat1_name = urllib.parse.quote(food_cat1_name)
    service_key = 'jL8PHgOttipXdQtu%2BF4YntDmduAIvBCSdB7OP5VHXLcT8zpvkYvHRaPOAiwsFhAZVTG9hy%2FiVtBt%2Bqn7aQmT0w%3D%3D'
    base = 'http://apis.data.go.kr/1471000/FoodNtrCpntDbInfo02'
    num_of_rows = 100
    page_no = 1
    templist = []

    while True:
        url = (
            f"{base}/getFoodNtrCpntDbInq02"
            f"?serviceKey={service_key}"
            f"&type=json"
            f"&FOOD_CAT1_NM={encoded_food_cat1_name}"
            f"&numOfRows={num_of_rows}"
            f"&pageNo={page_no}"
        )
        print(f"Requesting page {page_no}: {url}")

        try:
            response = requests.get(url, verify=False, timeout=10)
            response.raise_for_status()
            inform = response.json()

            body = inform.get('body', {})
            items = body.get('items', [])
            total_count = int(body.get('totalCount', 0))

            if not items:
                break  # 더 이상 데이터 없음

            for item in items:
                try:
                    serving_size_str = item.get('SERVING_SIZE', '100g')
                    serving_weight = parse_serving_size(serving_size_str)

                    portion_weight_str = item.get('Z10500', '')
                    if portion_weight_str:
                        try:
                            portion_weight = float(portion_weight_str)
                        except ValueError:
                            portion_weight = serving_weight
                    else:
                        portion_weight = serving_weight

                    kcal_100g = float(item.get('AMT_NUM1', 0) or 0)
                    kcal_portion = kcal_100g * (portion_weight / serving_weight)

                    tempdata = {
                        'food_name': item.get('FOOD_NM_KR'),
                        'calories': kcal_portion,
                        'carbohydrate': float(item.get('AMT_NUM6', 0) or 0),
                        'protein': float(item.get('AMT_NUM3', 0) or 0),
                        'fat': float(item.get('AMT_NUM4', 0) or 0),
                        'sugar': float(item.get('AMT_NUM7', 0) or 0),
                        'sodium': float(str(item.get('AMT_NUM13', '0')).replace(',', '') or 0)
                    }
                    templist.append(tempdata)
                    
                except Exception as item_err:
                    print(f"아이템 처리 오류: {item_err}")

            if page_no * num_of_rows >= total_count:
                break
            page_no += 1

        except Exception as e:
            print(f"요청 실패 (page {page_no}): {e}")
            break

    return templist

def remove_duplicates(foods):
    seen = set()
    unique_foods = []
    for food in foods:
        if food['food_name'] not in seen:
            unique_foods.append(food)
            seen.add(food['food_name'])
    return unique_foods

def get_range(nutrient, level):
    """
    nutrient: 'sodium', 'protein', 'carbohydrate', 'sugar' 중 하나
    level: 'low', 'standard', 'high'
    """
    if nutrient == 'sodium':
        # 나트륨(mg): 0~200 low, 201~500 standard, 501 이상 high
        if level == 'low':
            return (0, 200)
        elif level == 'standard':
            return (201, 500)
        elif level == 'high':
            return (501, float('inf'))
    elif nutrient == 'protein':
        # 단백질(g): 0~5 low, 5.1~10 standard, 10.1 이상 high
        if level == 'low':
            return (0, 5)
        elif level == 'standard':
            return (5.1, 10)
        elif level == 'high':
            return (10.1, float('inf'))
    elif nutrient == 'carbohydrate':
        # 탄수화물(g): 0~15 low, 15.1~30 standard, 30.1 이상 high
        if level == 'low':
            return (0, 15)
        elif level == 'standard':
            return (15.1, 30)
        elif level == 'high':
            return (30.1, float('inf'))
    elif nutrient == 'sugar':
        # 당류(g): 0~5 low, 5.1~10 standard, 10.1 이상 high
        if level == 'low':
            return (0, 5)
        elif level == 'standard':
            return (5.1, 10)
        elif level == 'high':
            return (10.1, float('inf'))
    # 기본 범위 (모든 값 허용)
    return (0, float('inf'))

def recommend_meals(foods, current_weight, target_weight, rdi, sodium, protein, sugar, restriction, recommend_count):
    current_weight = float(current_weight)
    target_weight = float(target_weight)
    rdi = float(rdi)
    recommend_count = int(recommend_count)

    if target_weight < current_weight:
        weight_change = 'loss'
    elif target_weight > current_weight:
        weight_change = 'gain'
    else:
        weight_change = 'maintain'

    if weight_change == 'loss':
        meal_calories = rdi * 0.3
    elif weight_change == 'gain':
        meal_calories = rdi * 0.4
    else:
        meal_calories = rdi * 0.33

    sodium_range = get_range('sodium', sodium)
    protein_range = get_range('protein', protein)
    sugar_range = get_range('sugar', sugar)

    filtered = []
    for food in foods:
        try:
            if food['calories'] > meal_calories:
                continue
            if not (sodium_range[0] <= food['sodium'] <= sodium_range[1]):
                continue
            if not (protein_range[0] <= food['protein'] <= protein_range[1]):
                continue
            if not (sugar_range[0] <= food['sugar'] <= sugar_range[1]):
                continue

            # 식단 제한 필터
            # if restriction == 'no-vegetable' and any(x in food['food_name'] for x in ['나물', '채소', '샐러드']):
            #     continue
            # if restriction == 'no-meat' and any(x in food['food_name'] for x in ['고기', '닭', '돼지', '소고기', '스팸']):
            #     continue

            filtered.append(food)
        except Exception as e:
            print(f"Filtering error: {e}")
            continue

    filtered = sorted(filtered, key=lambda x: x['calories'])
    filtered = remove_duplicates(filtered)
    filtered = filtered[:recommend_count]

    return filtered

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3000, debug=True)