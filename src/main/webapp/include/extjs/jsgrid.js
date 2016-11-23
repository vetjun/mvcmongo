Ext.onReady(function() {
			/*This javascript codes controlls the frontend of application for CRUD actions and Error actions*/
			var store = new Ext.data.Store({
				url: '/tutorial/user/issuers',
				reader: new Ext.data.JsonReader({
					root: 'issuers',
					id: 'id'
				}, [
                    'id',
					'name',
					'surname',
					'telephone'
				])
			});
			store.load();
			
			
			var ds_model = Ext.data.Record.create([
				'id',
				'name',
				'surname',
				'telephone'
			]);
			
			var ticker_edit = new Ext.form.TextField();
			var name_edit = new Ext.form.TextField();
			var type_edit = new Ext.form.TextField();
			var country_edit = new Ext.form.TextField();
			
			var sm2 = new Ext.grid.CheckboxSelectionModel();
			var grid = new Ext.grid.EditorGridPanel({
		        id:'button-grid',
		        store: store,
		        cm: new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
		            {id: 'id',header: "id", dataIndex: 'id', sortable: true},
					{ header: "Name", dataIndex: 'name', sortable: true, editor: name_edit},
					{header: "Surname", dataIndex: 'surname', sortable: true, width: 75, editor: type_edit},
					{header: "Telephone", dataIndex: 'telephone', sortable: true, width: 75, editor: country_edit}
		        ]),
		        
		        selModel: new Ext.grid.RowSelectionModel({
		            singleSelect: false
		        }),
		        
		        listeners: {
		        	beforeedit:    function (e, editor) {
		        		$("#ext-comp-1004").mask("(999) 999-9999");
		            },
		        	afteredit: function(e) {
			        	var _ticker = e.record.data.id;
			        	var _issuerName = (e.field == 'name') ? e.value : e.record.data.name;
			        	var _issuerType = (e.field == 'surname') ? e.value : e.record.data.surname;
			        	var _country = (e.field == 'telephone') ? e.value : e.record.data.telephone;
			        	
			        	var restURL = '/tutorial/user/issuer/update/'+_ticker;
			        	var conn = new Ext.data.Connection();
			        	$(".modal").show();
	            		conn.request({
		            		url: restURL,
		            		method: 'POST',
		            		params: {
		            			//id: _ticker,
			            		name: _issuerName,
			            		surname: _issuerType,
			            		telephone: _country
		            		},
		            		success: function(a, response) {
		            			e.record.commit();
		            			$(".modal").hide();
		            		    store.load();
                            },
                            failure: function(a, response) {
                            	$(".modal").hide();
                                Ext.Msg.alert("Failed", response.result.message);
                                e.record.reject();
                            }
	            		});
		        	}
		       	},
			
		        viewConfig: {
		            forceFit:true
		        },

		        // inline toolbars
		        tbar:[{
		            text:'Add User',
		            tooltip:'Add a new User',
		            icon: 'images/addIssuer16.png',
		            cls: 'x-btn-text-icon',
		            handler: function() {
		            	var fileref=document.createElement('script');
		                fileref.setAttribute("type","text/javascript");
		                fileref.setAttribute("src", "https://www.google.com/recaptcha/api.js?hl=en");
		                document.getElementsByTagName("head")[0].appendChild(fileref);
		            	var form = new Ext.form.FormPanel({
		    		        baseCls: 'x-plain',
		    		        labelWidth: 75,
		    		        width:500,
		    		        height:300,
		    		        name: 'MyForm',
		    		        url: '/tutorial/user/issuer/addIssuer',
		    		        defaultType: 'textfield',
		    		        items: [{
		    		            fieldLabel: 'User Name',
		    		            id: 'name',
		    		            name: 'name',
		    		            allowBlank:false,
		    		            anchor: '100%'  // anchor width by percentage
		    		        }, {
		    		        	fieldLabel: 'Surname',
		    		        	id: 'surname',
		    		            name: 'surname',
		    		            maxLength: 10,
		    		            width: 90
		    		        }, {
		    		        	xtype: 'textfield',
		    		        	fieldLabel: 'Telephone',
		    		        	id: 'telephone',
		    		            name: 'telephone',
		    		            maxLength: 20,		    		        
		    		            width: 150
		    		        },
		    		        {
		    	                xtype: 'panel',
		    	                itemId: 'reCaptcha',
		    	                id:'reCaptcha',
		    	                border: false,
		    	                html: '<div class="g-recaptcha"  data-sitekey="6LeXqAwUAAAAAGbBQF3DSJESgbMYO976FrFw7T7o"></div>'
		    	            },
		    	            {
		    	                xtype: 'panel',
		    	                id:'panelerror',
		    	                html: '<div id="errorpanel"></div>'
		    	            }
		    		        ]
		    		    });
		    			
		    			var window = new Ext.Window({
		    		        title: 'Add New User',
		    		        width: 320,
		    		        height:270,
		    		        minWidth: 320,
		    		        minHeight: 270,
		    		        layout: 'fit',
		    		        plain:true,
		    		        bodyStyle:'padding:5px;',
		    		        buttonAlign:'center',
		    		        resizable: false,
		    		        items: form,

		    		        buttons: [{
		    		            text: 'Save User',
		    		            handler: function () {
		    		            	//var formTicker = Ext.get('id').getValue();
		    		            	var formName = Ext.get('name').getValue();
		    		            	var formType = Ext.get('surname').getValue();
		    		            	var formCountry = Ext.get('telephone').getValue();
		    		            	var capcap = grecaptcha.getResponse();
		    		            	$(".modal").show();
		    		            	if (form.getForm().isValid()) {
			    		            	form.getForm().submit({
			    		            		method: 'POST',
			            					url: '/tutorial/user/issuer/addIssuer',
			                                success: function(a, response)
			                                {
				                              	grid.getStore().insert(
				          				            	0,
				          				            	new ds_model({
				          				            		//id: formTicker,
				  		            						name: formName,
				  		            						surname: formType,
				  		            						telephone: formCountry
				          				            	})
				           				        );
				                              	$(".modal").hide();
				    		            		window.close();
				    		            		store.load();
			                                },
			                                failure: function(a, response)
			                                {
			                                	$(".modal").hide();
			                                	$("#errorpanel").html(response.result.message);
			                                    //Ext.Msg.alert("Failed", response.result.message);
			                                     
			                                }
			                            });
		    		            	}
		    		            }
		    		        },{
		    		            text: 'Cancel',
		    		            handler: function () {
		    		            	if (window) {
		    		            		window.close();
		    		            	}
		    		            }
		    		        }]
		    		    });
		    			
		            	window.show();
		            	$("#telephone").mask("(999) 999-9999");
		            }
		        },'-',{
		            text:'Remove User',
		            tooltip:'Remove the selected User',
		            icon: 'images/removeIssuer16.png',
		            cls: 'x-btn-text-icon',
		            handler: function() {
		            	var sm = grid.getSelectionModel();
		            	var sel = sm.getSelected();
		            	if (sm.hasSelection()) {
		            		Ext.Msg.show({
		            			title: 'Remove User',
		            			buttons: Ext.MessageBox.YESNOCANCEL,
		            			msg: 'Remove ' + sel.data.name + '?',
		            			fn: function(btn) {
		            				if (btn == 'yes') {
		            					var conn = new Ext.data.Connection();
		            					$(".modal").show();
		            					var restURL = '/tutorial/user/issuer/delete/' + sel.data.id;
		            					conn.request({
		            						method: 'DELETE',
			            					url: restURL,
			            					success: function(resp,opt) {
			            						grid.getStore().remove(sel);
			            						$(".modal").hide();
			            					},
			            					failure: function(resp,opt) {
			            						$(".modal").hide();
				            					Ext.Msg.alert('Error', 'Unable to delete issuer');
				            				}
			            				});
		            				}
		            			}
		            		});
		            	};
		            }
		        },'-',{
		            text:'Refresh Table',
		            tooltip:'Refresh Table',
		            icon: 'images/addIssuer16.png',
		            cls: 'x-btn-text-icon',
		            handler: function() {
		            	store.load();
		            }
		        }],

		        width: 600,
		        height: 350,
		        collapsible: true,
		        frame: true,
		        clicksToEdit: 2,
		        animCollapse: false,
		        title:'User Management Panel With MongoDB Access',
		        iconCls:'icon-grid',
		        renderTo: document.body
		    });
		});