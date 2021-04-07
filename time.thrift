namespace java com.pay.fakepay.thrift

struct TimestampResponse {
	1: required i32 year;
	2: required i32 month;
	3: required i32 date;
	4: required i32 hrs;
	5: required i32 min;
	6: required i32 sec;
}

service TimestampService {
	TimestampResponse timestamp()
}