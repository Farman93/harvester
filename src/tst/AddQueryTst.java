package tst;

import dbInteractions.DBAddQuery;

public class AddQueryTst {
	public static void main(String[] args) {
		int query_id = DBAddQuery.dbConnect("name","endUrl","startURL","1","14.11.2013 18:00","1","29","somestruct");
		System.out.println(query_id);
	}
}
